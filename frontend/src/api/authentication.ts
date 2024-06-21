"use server";

import { cookies } from "next/headers";
import { NextRequest, NextResponse } from "next/server";
import { redirect, RedirectType } from "next/navigation";

interface LoginResponse {
  token: string;
  expiresIn: number;
  roles: { id: number; name: string; description: string }[];
}

export interface User {
  id: number;
  name: string;
  profilePicturePath: string;
  createdAt: string;
  roles: { id: number; name: string; description: string }[];
  articles: string[];
}

export async function register(formData: FormData) {
  try {
    const name = formData.get("name") as string;
    const email = formData.get("email") as string;
    const password = formData.get("password") as string;

    const response = await fetch("http://localhost:8080/auth/signup", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name, email, password }),
    });

    if (!response.ok) {
      return;
    }
  } catch (error) {
    redirect("/");
  }

  redirect("/login", RedirectType.replace);
}

export async function login(formData: FormData) {
  try {
    const email = formData.get("email") as string;
    const password = formData.get("password") as string;

    const response = await fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password }),
    });

    if (!response.ok) {
      return;
    }

    const loginResponse: LoginResponse = await response.json();

    const expires = new Date(Date.now() + loginResponse.expiresIn);
    const session = loginResponse.token;

    const user = await fetch("http://localhost:8080/client/me", {
      cache: "no-cache",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${session}`,
      },
    });

    if (!user.ok) {
      return;
    }

    const userResponse: User = await user.json();

    cookies().set("session", session, { expires, httpOnly: true });
    cookies().set("user", JSON.stringify(userResponse), {
      expires,
      httpOnly: true,
    });
  } catch (error) {
    redirect("/");
  }

  redirect("/", RedirectType.replace);
}

export async function logout() {
  cookies().set("session", "", { expires: new Date(0) });
  cookies().set("user", "", { expires: new Date(0) });
  redirect("/", RedirectType.replace);
}

export async function getSession() {
  const session = cookies().get("session")?.value;
  if (!session) return null;
  return session;
}

export async function getUser() {
  const user = cookies().get("user")?.value;
  if (!user) return null;
  return JSON.parse(user) as User;
}

export async function updateSession(request: NextRequest) {
  const session = request.cookies.get("session")?.value;
  if (!session) return;
  return NextResponse.next();
}