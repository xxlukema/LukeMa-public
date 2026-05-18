# Next.js

    # this will install node.js:
    npm i -g n

## Add external link to image

    // `next.config.ts`:
    // <https://upload.wikimedia.org/wikipedia/en/e/e1/University_of_Texas_at_Austin_seal.svg>
    import type { NextConfig } from "next";
    
    const nextConfig: NextConfig = {
      /* config options here */
      images: {
        remotePatterns: [
          {
            protocol: "https",
            hostname: "wikipedia.org"
          },
        ]
      }
    };
    
    export default nextConfig;

## Suspension and streaming

### With React, we have "Loading..." as

    <Layout>
       <Header />
       <sideNav />
       <Suspense fallback={<Loading />}>
          </Page />
       </Suspense>
    </Layout>

### With Next.js, all we needed is a `loaing.tsx` file in the same level of `page.tsx`

## Caching

- Client side cache default times out in 5 minutes.
- Route cache

    export const dynamic = "force-dynamic"

## Middleware

## Folder structure

## Navivation

### `useRouter()` hook: for Client Components

    'use client'
     
    import { useRouter } from 'next/navigation'
     
    export default function Page() {
      const router = useRouter()
     
      return (
        <button type="button" onClick={() => router.push('/dashboard')}>
          Dashboard
        </button>
      )
    }

### `redirect` function: For Server Components, use the redirect function instead

    import { redirect } from 'next/navigation'
    
    redirect('/login')
