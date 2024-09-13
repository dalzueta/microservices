import { NextResponse } from 'next/server';

export function middleware(req) {
  const token = req.cookies.token;

  if (!token && req.nextUrl.pathname === '/dashboard') {
    return NextResponse.redirect('/');
  }

  return NextResponse.next();
}
