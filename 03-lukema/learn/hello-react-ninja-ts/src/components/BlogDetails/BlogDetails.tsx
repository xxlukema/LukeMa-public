import useFetch, { Blog } from "@/UseFetch/UseFetch";
import React from 'react';
import { useHistory, useParams } from "react-router-dom";
import styles from './BlogDetails.module.scss';

const BlogDetails = () => {

  const { id } = useParams<{ id: string }>();

  const { data: blog, error, isPending } = useFetch<Blog>('http://localhost:8000/blogs/' + id);
  const history = useHistory();

  const handleClick = () => {
    fetch('http://localhost:8000/blogs/' + blog?.id, {
      method: 'DELETE'
    }).then(() => {
      history.push('/');
    })
  }

  return (
    <div className={styles.BlogDetails}>
      { isPending && <div>Loading...</div>}
      { error && <div>{error}</div>}
      { blog && (
        <article>
          <h2>{blog.title}</h2>
          <p>Written by {blog.author}</p>
          <div>{blog.body}</div>
          <button onClick={handleClick}>delete</button>
        </article>
      )}
    </div>
  );
}


export default BlogDetails;
