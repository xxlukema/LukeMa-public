import React from 'react';
import { Link } from 'react-router-dom';
import styles from './BlogList.module.scss';
import { Blog } from '@/UseFetch/UseFetch'

const BlogList = ({ blogs }: { blogs: Array<Blog> }) => {
  return (
    <div>
      {blogs.map((blog: Blog) => (
        <div className={styles.BlogList} key={blog.id} >
          <Link to={`/blogs/${blog.id}`}>
            <h2>{blog.title}</h2>
            <p>Written by {blog.author}</p>
          </Link>
        </div>
      ))}
    </div>
  );
};

export default BlogList;
