"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { fetchUsers, User, flipRank } from "@/api/authentication";
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

export default function AdminPage() {
  const router = useRouter();
  const [users, setUsers] = useState<User[] | null>(null);

  useEffect(() => {
    async function getUsers() {
      const response = await fetchUsers();
      setUsers(response);
    }

    getUsers();
  }, []);

  return (
    <Container maxWidth="md">
      <Box sx={{ my: 4, display: "flex", flexDirection: "column", gap: 4 }}>
        <Typography variant="h2">Current users:</Typography>
        <List>
          {users?.map((user) => (
            <ListItem
              key={user.id}
              secondaryAction={
                <IconButton
                  edge="end"
                  type="submit"
                  onClick={() => {
                    flipRank(user);
                    location.reload();
                  }}
                >
                  <EditIcon
                    color={
                      user.roles.some((role) => role.name === "EDITOR")
                        ? "primary"
                        : "disabled"
                    }
                  />
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
          ))}
        </List>
      </Box>
    </Container>
  );
}
