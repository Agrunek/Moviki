import { redirect, RedirectType } from "next/navigation";
import { getSession, getUser } from "@/api/authentication";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import TextField from "@mui/material/TextField";
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
    const session = await getSession();
    const user = await getUser();

    const response = await fetch(`http://localhost:8080/article/${title}`, {
      cache: "no-cache",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${session}`,
      },
    });

    if (!response.ok) {
      throw new Error(`${response.status}: Article data fetch failed!`);
    }

    const result: Article = await response.json();

    if (result.client !== user?.name) {
      throw new Error(`${response.status}: Article author doesn't match!`);
    }

    return result;
  } catch (error) {
    redirect("/", RedirectType.replace);
  }
}

export default async function ArticleEditPage({
  params,
}: {
  params: { title: string };
}) {
  const article: Article = await fetchArticle(params.title);

  async function updateArticle(formData: FormData) {
    "use server";

    try {
      const session = await getSession();
      const content = formData.get("content") as string;

      const response = await fetch(
        `http://localhost:8080/article/${params.title}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${session}`,
          },
          body: JSON.stringify(content),
        }
      );

      if (!response.ok) {
        throw new Error(`${response.status}: Article couldn't be updated!`);
      }
    } catch (error) {
      redirect("/", RedirectType.replace);
    }

    redirect(`/article/${params.title}`, RedirectType.replace);
  }

  return (
    <Container maxWidth="lg">
      <Box
        component="form"
        action={updateArticle}
        sx={{ my: 4, display: "flex", flexDirection: "column", gap: 8 }}
      >
        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "flex-end",
          }}
        >
          <Typography variant="h2">{article.title}</Typography>
          <Typography variant="h5">by {article.client}</Typography>
        </Box>
        <TextField
          required
          fullWidth
          multiline
          id="content"
          name="content"
          autoFocus
          defaultValue={article.content}
        />
        <Button
          type="submit"
          variant="contained"
          sx={{ alignSelf: "flex-end" }}
        >
          SAVE
        </Button>
      </Box>
    </Container>
  );
}
