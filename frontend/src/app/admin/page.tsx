import { redirect, RedirectType } from "next/navigation";
import { getSession } from "@/api/authentication";
import Avatar from "@mui/material/Avatar";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import EditIcon from "@mui/icons-material/Edit";
import IconButton from "@mui/material/IconButton";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import ListItemText from "@mui/material/ListItemText";
import PersonIcon from "@mui/icons-material/Person";
import Typography from "@mui/material/Typography";

interface User {
  id: number;
  name: string;
  profilePicturePath: string;
  createdAt: string;
  roles: { id: number; name: string; description: string }[];
}

async function fetchUsers(): Promise<User[]> {
  try {
    const session = await getSession();

    const response = await fetch("http://localhost:8080/client/", {
      cache: "no-cache",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${session}`,
      },
    });

    if (!response.ok) {
      throw new Error(`${response.status}: Users data fetch failed!`);
    }

    return response.json();
  } catch (error) {
    redirect("/", RedirectType.replace);
  }
}

function buildListItem(user: User): React.ReactNode {
  return (
    <ListItem
      key={user.id}
      secondaryAction={
        <IconButton edge="end">
          <EditIcon />
        </IconButton>
      }
    >
      <ListItemAvatar>
        <Avatar>
          <PersonIcon />
        </Avatar>
      </ListItemAvatar>
      <ListItemText primary={user.name} />
    </ListItem>
  );
}

export default async function AdminPage() {
  const users: User[] = await fetchUsers();

  return (
    <Container maxWidth="md">
      <Box sx={{ my: 4, display: "flex", flexDirection: "column", gap: 4 }}>
        <Typography variant="h2">Current users:</Typography>
        <List>{users.map((user) => buildListItem(user))}</List>
      </Box>
    </Container>
  );
}
