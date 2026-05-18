"use client"

import React from 'react'


type Post = {
  id: number;
  title: string;
  body: string;
};

const PostPage = () => {
  const [data, setData] = React.useState<{ posts: Post[]; total: number }>({
    posts: [],
    total: 0,
  });

  /**
   * If `use client` is used, the `fetch` request must be done inside a `useEffect` hook.
   */
  React.useEffect(() => {
    const fetchPosts = async () => {
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

      /**
       * The `setData` function is used to update the state of the component.
       */
      setData({
        posts: data.posts,
        total: data.total,
      });
    };

    /**
     * Fetch posts from the API when the component mounts.
     */
    fetchPosts();
  }, []);

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
