import { redirect, RedirectType } from "next/navigation";
import { getSession, getUser } from "@/api/authentication";
import DeleteIcon from "@mui/icons-material/Delete";
import Markdown from "react-markdown";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import EditIcon from "@mui/icons-material/Edit";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";

interface Article {
  title: string;
  content: string;
  mainImagePath: string;
  client: string;
  updatedAt: string;
}

async function fetchArticle(title: string): Promise<Article> {
  try {
    const response = await fetch(`http://localhost:8080/article/${title}`, {
      cache: "no-cache",
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      throw new Error(`${response.status}: Article data fetch failed!`);
    }

    return response.json();
  } catch (error) {
    redirect("/", RedirectType.replace);
  }
}

export default async function ArticlePage({
  params,
}: {
  params: { title: string };
}) {
  const article: Article = await fetchArticle(params.title);
  const user = await getUser();

  async function deleteArticle() {
    "use server";

    try {
      const session = await getSession();

      await fetch(`http://localhost:8080/article/${params.title}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${session}`,
        },
      });
    } catch (error) {
      redirect("/", RedirectType.replace);
    }
    redirect("/", RedirectType.replace);
  }

  return (
    <Container maxWidth="lg">
      <Box sx={{ my: 4, display: "flex", flexDirection: "column", gap: 4 }}>
        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "flex-end",
          }}
        >
          <Typography variant="h2">{article.title}</Typography>
          {article.client === user?.name ? (
            <Box sx={{ display: "flex", alignItems: "center", gap: 4 }}>
              <IconButton href={`/article/${params.title}/edit`}>
                <EditIcon />
              </IconButton>
              <Box component="form" action={deleteArticle}>
                <IconButton type="submit">
                  <DeleteIcon />
                </IconButton>
              </Box>
            </Box>
          ) : (
            <Typography variant="h5">by {article.client}</Typography>
          )}
        </Box>
        <Markdown>{article.content}</Markdown>
      </Box>
    </Container>
  );
}
