# Next.js `use client` vs `use server`

No async Client Component

## 1. `use server` is the default action

"SSR" or "Dynamic Rendering".

1. No need to write (optional) `use server`
2. If `user server` is used, or not specified, the web component can be made `async`

## 2. If `use client` is used, the web component cannot be `async`, and `fetch` must be done inside `useEffect<>()..., [])

- If `use client` is used, the `fetch` request must be done inside a `useEffect` hook (initialization hook []).
- However, the call to fetch can be executed four times. ---- need research.
