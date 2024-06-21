import { redirect, RedirectType } from "next/navigation";
import { getSession, getUser } from "@/api/authentication";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";

async function tryLoad() {
  const user = await getUser();
  if (
    user?.roles?.some((role) => role.name === "ADMIN" || role.name === "EDITOR")
  ) {
    return;
  }
  redirect("/", RedirectType.replace);
}

export default async function ArticleEditPage() {
  async function createArticle(formData: FormData) {
    "use server";

    const session = await getSession();
    const title = formData.get("title") as string;
    const content = formData.get("content") as string;

    try {
      const response = await fetch(`http://localhost:8080/article/${title}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${session}`,
        },
        body: JSON.stringify(content),
      });

      if (!response.ok) {
        throw new Error(`${response.status}: Article couldn't be updated!`);
      }
    } catch (error) {
      redirect("/", RedirectType.replace);
    }

    redirect(`/article/${title}`, RedirectType.replace);
  }

  return (
    <Container maxWidth="lg">
      <Box
        component="form"
        action={createArticle}
        sx={{ my: 4, display: "flex", flexDirection: "column", gap: 8 }}
      >
        <TextField required fullWidth id="title" name="title" autoFocus />
        <TextField required fullWidth multiline id="content" name="content" />
        <Button
          type="submit"
          variant="contained"
          sx={{ alignSelf: "flex-end" }}
        >
          CREATE
        </Button>
      </Box>
    </Container>
  );
}
