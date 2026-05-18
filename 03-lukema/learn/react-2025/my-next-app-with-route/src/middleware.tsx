import { NextRequest, NextResponse } from 'next/server'

/**
 * Middleware is a function that runs before the request is processed.
 * It can be used to modify the request or response, or to perform authentication.
 */
const middleware = (request: NextRequest) => {

  // const isAuthenticated = request.cookies.get('isAuthenticated')?.value === 'true'

  /** const isAuthenticated = request.cookies.get('isAuthenticated')?.value === 'true' || sessionStorage.getItem('isAuthenticated') === 'true' */
  /** const isAuthenticated = sessionStorage.getItem('isAuthenticated') === 'true' */
  const isAuthenticated = request.cookies.get('isAuthenticated')?.value === 'true'

  // const isLoginPage = request.nextUrl.pathname === '/login'

  console.log('isAuthenticated', isAuthenticated)
  console.log('request.nextUrl.pathname', request.nextUrl.pathname)

  if (!isAuthenticated) {
    return NextResponse.redirect(new URL('/login', request.url))
  }

  return NextResponse.next()
}

export default middleware

export const config = {
  matcher: ['/', '/postbyserver/:path*', '/postbyclient/:path*', '/login_not guarded/:path*'],
}
