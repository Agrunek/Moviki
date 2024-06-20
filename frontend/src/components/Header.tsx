"use client";

import { useState } from "react";
import { logout } from "@/api/authentication";
import SearchBar from "@/components/SearchBar";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import IconButton from "@mui/material/IconButton";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";

export default function Header(props: { session: string | null }) {
  const [anchor, setAnchor] = useState<null | HTMLElement>(null);

  function handleMenu(event: React.MouseEvent<HTMLElement>) {
    setAnchor(event.currentTarget);
  }

  function handleClose() {
    setAnchor(null);
  }

  return (
    <Box sx={{ flexGrow: 1, p: 2 }}>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Moviki
          </Typography>
          <Box sx={{ display: "flex", gap: 4, alignItems: "center" }}>
            <SearchBar />
            {props.session ? (
              <>
                <IconButton size="large" onClick={handleMenu} color="inherit">
                  <AccountCircleIcon />
                </IconButton>
                <Menu
                  id="menu-appbar"
                  anchorEl={anchor}
                  anchorOrigin={{
                    vertical: "top",
                    horizontal: "right",
                  }}
                  keepMounted
                  transformOrigin={{
                    vertical: "top",
                    horizontal: "right",
                  }}
                  open={Boolean(anchor)}
                  onClose={handleClose}
                >
                  <MenuItem onClick={() => window.location.replace("/me")}>
                    Account
                  </MenuItem>
                  <MenuItem onClick={async () => await logout()}>
                    Log out
                  </MenuItem>
                </Menu>
              </>
            ) : (
              <Button color="inherit" href="/login">
                LOG IN
              </Button>
            )}
          </Box>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
