import { register } from "@/api/authentication";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import Link from "@mui/material/Link";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";

export default function RegisterPage() {
  return (
    <Container maxWidth="sm">
      <Box
        component="form"
        action={register}
        sx={{
          my: 4,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          gap: 6,
        }}
      >
        <Typography variant="h2">REGISTER</Typography>
        <TextField
          required
          fullWidth
          id="name"
          label="name"
          name="name"
          autoComplete="name"
          autoFocus
        />
        <TextField
          required
          fullWidth
          id="email"
          label="Email"
          name="email"
          autoComplete="email"
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
        <Link href="/login" variant="body2">
          Already have the account? Log in.
        </Link>
      </Box>
    </Container>
  );
}
