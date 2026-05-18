'use client'
import React from 'react'
import { useRouter } from 'next/navigation'

const Login = () => {
  const router = useRouter()

  const handleLogin = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    const formData = new FormData(event.currentTarget)
    const username = formData.get('username')
    const password = formData.get('password')

    // Perform login logic here
    console.log('Logging in with:', { username, password })

    if (username === 'luke' && password === 'test') {
      // Set authentication cookie or session
      document.cookie = 'isAuthenticated=true; path=/'
      // sessionStorage.setItem('isAuthenticated', 'true')
      router.push('/')
    } else {
      alert('Invalid credentials')
    }
  }

  return (
    <>
      <div className='text-center text-2xl'>Login</div>
      <form onSubmit={handleLogin} className="flex flex-col gap-4 items-center">
        <div className="flex gap-4">
          <label htmlFor="username">Username</label>
          <input type="text" id="username" name="username" placeholder="luke" required />
        </div>
        <div className="flex gap-4">
          <label htmlFor="password">Password</label>
          <input type="password" id="password" name="password" placeholder="test" required />
        </div>
        <button type="submit">Submit</button>
      </form>
      {/* <div className='text-center mt14'>LLoading...</div> */}
    </>
  )
}

export default Login
