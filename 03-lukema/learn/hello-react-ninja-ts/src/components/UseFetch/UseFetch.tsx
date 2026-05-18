import { useEffect, useState } from 'react';

export interface Blog {
  id: number,
  title: string,
  author: string,
  body: string
};

const useFetch = <T extends Object | null>(url: string): { data: T | null, isPending: boolean, error: any } => {
  const [data, setData] = useState<T | null>(null);
  const [isPending, setIsPending] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const abortController = new AbortController();

    setTimeout(() => {
      fetch(url, { signal: abortController.signal })
        .then(res => {
          if (!res.ok) { // error coming back from server
            throw Error('could not fetch the data for that resource');
          }
          return res.json();
        })
        .then(data => {
          setIsPending(false);
          setData(data);
          setError(null);
        })
        .catch(err => {
          if (err.name === 'AbortError') {
            console.log('fetch aborted')
          } else {
            // auto catches network / connection error
            setIsPending(false);
            setError(err.message);
          }
        })
    }, 5_000);

    // abort the fetch
    return () => abortController.abort();
  }, [url])

  return { data, isPending, error };
};

export default useFetch;
