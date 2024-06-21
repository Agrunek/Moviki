import { redirect, RedirectType } from "next/navigation";
import { getSession, getUser } from "@/api/authentication";
import Avatar from "@mui/material/Avatar";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import Divider from "@mui/material/Divider";
import Link from "@mui/material/Link";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import ListItemText from "@mui/material/ListItemText";
import PersonIcon from "@mui/icons-material/Person";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";

interface Comment {
  id: number;
  content: string;
  articleTitle: string;
  clientName: string;
  createdAt: string;
}

async function fetchComments(title: string): Promise<Comment[]> {
  try {
    const response = await fetch(`http://localhost:8080/comment/${title}`, {
      cache: "no-cache",
      headers: { "Content-Type": "application/json" },
    });

    if (!response.ok) {
      throw new Error(`${response.status}: Comments data fetch failed!`);
    }

    return response.json();
  } catch (error) {
    redirect("/", RedirectType.replace);
  }
}

function buildListItem(comment: Comment): React.ReactNode {
  return (
    <>
      <ListItem key={comment.id}>
        <ListItemAvatar>
          <Avatar>
            <PersonIcon />
          </Avatar>
        </ListItemAvatar>
        <ListItemText primary={comment.content} />
      </ListItem>
      <Divider variant="inset" component="li" sx={{ my: 2 }} />
    </>
  );
}

export default async function AdminPage({
  params,
}: {
  params: { title: string };
}) {
  const comments: Comment[] = await fetchComments(params.title);
  const user = await getUser();

  async function createComment(formData: FormData) {
    "use server";

    const session = await getSession();
    const content = formData.get("content") as string;

    try {
      const response = await fetch(
        `http://localhost:8080/comment/${params.title}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${session}`,
          },
          body: JSON.stringify(content),
        }
      );

      if (!response.ok) {
        throw new Error(`${response.status}: Comment couldn't be created!`);
      }
    } catch (error) {
      redirect("/", RedirectType.replace);
    }

    redirect(`/article/${params.title}/discussion`, RedirectType.replace);
  }

  return (
    <Container maxWidth="md">
      <Box sx={{ my: 4, display: "flex", flexDirection: "column", gap: 4 }}>
        <Typography variant="h2">
          Discussion for article: {decodeURIComponent(params.title)}
        </Typography>
        <Link href={`/article/${params.title}`} variant="h5">
          Article
        </Link>
        {user?.name && (
          <Box
            component="form"
            action={createComment}
            sx={{
              mb: 4,
              display: "flex",
              alignItems: "center",
              gap: 4,
            }}
          >
            <TextField
              required
              fullWidth
              multiline
              id="content"
              name="content"
              label="Write new comment..."
            />
            <Button
              type="submit"
              variant="contained"
              sx={{ alignSelf: "flex-end" }}
            >
              POST
            </Button>
          </Box>
        )}
        <List>
          {comments.reverse().map((comment) => buildListItem(comment))}
        </List>
      </Box>
    </Container>
  );
}
