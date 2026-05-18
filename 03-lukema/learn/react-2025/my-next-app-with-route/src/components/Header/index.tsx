'use client'

import Link from 'next/link'
import React from 'react'
import Image from 'next/image'
import { redirect } from 'next/navigation'


const signout = async () => {
  document.cookie = 'isAuthenticated=false; path=/'
  // sessionStorage.setItem('isAuthenticated', 'false')
  redirect('/login')
}

const Header = () => {
  return (
    <div className="flex justify-between items-center bg-gray-800 text-white p-4">
      <Image src="https://upload.wikimedia.org/wikipedia/en/e/e1/University_of_Texas_at_Austin_seal.svg" alt='logo'
        className='h-[35px] w-[35px]' height={50} width={50} />
      <ul className="flex justify-end items-center gap-4">
        <li>
          <Link href="/">Home</Link>
        </li>
        <li>
          <Link href="/postbyclient">Post by Client (Why fetched four times?)</Link>
        </li>
        <li>
          <Link href="/postbyserver">Post by Server</Link>
        </li>
        <li>
          <button onClick={signout}>Exit</button>
        </li>
        {/* <li>
          <Link href="/postbyserver/[id]">Post by Server with id</Link>
        </li> */}
      </ul>
    </div>
  )
}

export default Header
