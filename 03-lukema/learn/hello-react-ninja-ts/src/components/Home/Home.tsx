import BlogList from '@/BlogList/BlogList';
import useFetch, { Blog } from '@/UseFetch/UseFetch';
import React from 'react';
import { FaSpinner } from 'react-icons/fa';
import styles from './Home.module.scss';

const Home = () => {

  const { error, isPending, data: blogs } = useFetch<Blog[]>('http://localhost:8000/blogs');

  console.log('home', blogs);

  return (
    <div className={styles.Home}>
      { error && <div>{error}</div>}
      { isPending &&
        <div>
          <FaSpinner className="{styles.Home.spinner}" ></FaSpinner>

          <span style={{
            marginLeft: '0.5em',
            marginTop: '0.1em'
          }}>
           Loading...
          </span>
        </div>
      }
      { blogs && <BlogList blogs={blogs} />}
    </div>
  );
}

export default Home;
