import { redirect, RedirectType } from "next/navigation";
import { getSession } from "@/api/authentication";
import Avatar from "@mui/material/Avatar";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import IconButton from "@mui/material/IconButton";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import PersonIcon from "@mui/icons-material/Person";
import Typography from "@mui/material/Typography";
import VisibilityIcon from "@mui/icons-material/Visibility";

interface User {
  id: number;
  name: string;
  profilePicturePath: string;
  createdAt: string;
  roles: { id: number; name: string; description: string }[];
  articles: string[];
}

async function fetchUser(): Promise<User> {
  try {
    const session = await getSession();

    const response = await fetch("http://localhost:8080/client/me", {
      cache: "no-cache",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${session}`,
      },
    });

    if (!response.ok) {
      throw new Error(`${response.status}: User data fetch failed!`);
    }

    return response.json();
  } catch (error) {
    redirect("/", RedirectType.replace);
  }
}

function buildListItem(article: string): React.ReactNode {
  return (
    <ListItem
      key={article}
      secondaryAction={
        <IconButton edge="end" href={`/article/${article}`}>
          <VisibilityIcon />
        </IconButton>
      }
    >
      <ListItemText
        primary={article}
        primaryTypographyProps={{ fontSize: "1.5rem" }}
      />
    </ListItem>
  );
}

export default async function AccountPage() {
  const user: User = await fetchUser();

  return (
    <Container maxWidth="md">
      <Box sx={{ my: 4, display: "flex", flexDirection: "column", gap: 4 }}>
        <Box
          sx={{
            display: "flex",
            flexDirection: "row",
            alignItems: "center",
            gap: 2,
          }}
        >
          <Avatar sx={{ width: 60, height: 60 }}>
            <PersonIcon sx={{ width: "60%", height: "60%" }} />
          </Avatar>
          <Typography variant="h2">
            {user.name}
            {user.roles.some((role) => role.name === "ADMIN")
              ? " (Admin)"
              : user.roles.some((role) => role.name === "EDITOR")
              ? " (Editor)"
              : ""}
          </Typography>
        </Box>
        <Typography variant="h4">My articles:</Typography>
        <List>{user.articles.map((article) => buildListItem(article))}</List>
      </Box>
    </Container>
  );
}
