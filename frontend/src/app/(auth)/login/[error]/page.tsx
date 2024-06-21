import { login } from "@/api/authentication";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import Link from "@mui/material/Link";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";

export default async function ArticlePage({
  params,
}: {
  params: { error: string };
}) {
  return (
    <Container maxWidth="sm">
      <Box
        component="form"
        action={login}
        sx={{
          my: 4,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          gap: 4,
        }}
      >
        <Typography variant="h2">LOG IN</Typography>
        <TextField
          required
          fullWidth
          id="email"
          label="Email"
          name="email"
          autoComplete="email"
          autoFocus
        />
        <TextField
          required
          fullWidth
          name="password"
          label="Password"
          type="password"
          id="password"
          autoComplete="current-password"
        />
        <Button type="submit" fullWidth variant="contained">
          CONFIRM
        </Button>
        <Typography variant="h6" color="red">
          {decodeURIComponent(params.error)}
        </Typography>
        <Link href="/register" variant="body2">
          Don't have an account? Join now.
        </Link>
      </Box>
    </Container>
  );
}
