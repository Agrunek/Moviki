import { NextRequest } from "next/server";
import { updateSession } from "@/api/authentication";

export async function middleware(request: NextRequest) {
  return await updateSession(request);
}
