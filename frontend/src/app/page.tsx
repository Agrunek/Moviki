import Image from "next/image";
import Box from "@mui/material/Box";
import logo from "@/assets/logo.png";

export default function HomePage() {
  return (
    <Box sx={{ mt: 20, display: "flex", justifyContent: "center" }}>
      <Image src={logo} alt="Logo" width={149} height={140} />
    </Box>
  );
}
