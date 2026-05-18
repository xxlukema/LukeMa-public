"use server"   // optional for server-side rendering

import React from 'react'


type Post = {
  id: number;
  title: string;
  body: string;
};

/**
 * server-side rendering function can be `async`
 */
const PostPage = async () => {

  /**
   * If `use server` is used, the `fetch` request can be done directly in the component.
   * No need to use `useEffect` or `useState`.
   */
  "use server"   // optional for server-side rendering

  /**
   * No need to use `useEffect` or `useState`.
   */
  const response = await fetch('https://dummyjson.com/posts?limit=3', {
    cache: 'no-store',
    // cache: 'force-cache', // 5 minutes
    // next: { revalidate: 60 }, // 1 minute
    // next: { revalidate: 60, tags: ['users'] }, // 1 minute
    // next: { revalidate: 60, tags: ['users'], fetchCache: 'force-cache' }, // 1 minute
    // next: { revalidate: 60, tags: ['users'], fetchCache: 'force-cache', nextCache: 'force-cache' }, // 1 minute
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    },
  });

  const data = await response.json();
  console.log(data);

  return (
    <div className='flex flex-col items-center justify-center h-screen'>
      <h3 className='text-3xl'>Posts</h3>
      <ul>
        {data.posts.map((post: Post) => (
          <li className='text-center' key={post.id}>
            <h4>{post.title}</h4>
          </li>
        ))}
      </ul>
      <h4>Post Count: {data.total}</h4>
    </div>
  );
}

export default PostPage
