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
      throw "Register went wrong...";
    }
  } catch (error) {
    redirect(`/register/${error}`, RedirectType.replace);
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
      throw "Login went wrong...";
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
      throw "Login went wrong...";
    }

    const userResponse: User = await user.json();

    cookies().set("session", session, { expires, httpOnly: true });
    cookies().set("user", JSON.stringify(userResponse), {
      expires,
      httpOnly: true,
    });
  } catch (error) {
    redirect(`/login/${error}`, RedirectType.replace);
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

export async function fetchUsers(): Promise<User[]> {
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

export async function flipRank(user: User) {
  try {
    const session = await getSession();
    const isEditor = user.roles.some((role) => role.name === "EDITOR");

    const response = await fetch("http://localhost:8080/client/role", {
      method: isEditor ? "DELETE" : "POST",
      cache: "no-cache",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${session}`,
      },
      body: JSON.stringify({ name: user.name, role: "EDITOR" }),
    });

    if (!response.ok) {
      throw new Error(`${response.status}: Users data fetch failed!`);
    }
  } catch (error) {
    redirect("/", RedirectType.replace);
  }
}
