import React from 'react'

interface User {
  id: number
  name: string
}

const UsersPage = async () => {

  /**
   * `cache` is only available in `typescript` built-in `fetch`. not available in `axois`
   * 
   * fetch('https://jsonplaceholder.typicode.com/users')
   *   .then(response => response.json())
   *   .then(josn => console.log(json))
   */
  const res = await fetch(
    'https://jsonplaceholder.typicode.com/users',
    {
      cache: 'no-store',
      // cache: 'force-cache', // 5 minutes
      // next: { revalidate: 60 }, // 1 minute
      // next: { revalidate: 60, tags: ['users'] }, // 1 minute
      // next: { revalidate: 60, tags: ['users'], fetchCache: 'force-cache' }, // 1 minute
      // next: { revalidate: 60, tags: ['users'], fetchCache: 'force-cache', nextCache: 'force-cache' }, // 1 minute
    }
  )
  const users: User[] = await res.json()

  return (
    <>
      <h2>Rendered at {new Date().toLocaleTimeString()}</h2>
      <div>Users</div>
      <ul>
        {users.map((user) => (
          <li key={user.id}>
            {user.name}
          </li>
        ))}
      </ul>
    </>
  )
}

export default UsersPage
