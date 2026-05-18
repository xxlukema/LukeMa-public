# Next.js

    rafce

## JSONplaceholder

    <jsonplaceholder.typicode.com>

    fetch('https://jsonplaceholder.typicode.com/todos/1')
         .then(response => response.json())
         .then(josn => console.log(json))

## Client-side vs server-side rendering

- By default, server-side rendering
- react-router-dom does not support server-side rendering. Therefore, it should be removed.
- 'use client'
- server-side fetching (no state)
- cache: `cache` is only available in `typescript` built-in `fetch`. not available in `axois`
- rendering: **static (build time)** vs **dynamic**. When the page has `fetch` and cache is enabled, Next.js will treat the page as static page.

## TODO

- Learn `@Tailwind`
- `npm i -D daisyui`
- `npm i -D scss`
- Extension: `Tailwind CSS IntelliSense` (by Tailwind Labs)
