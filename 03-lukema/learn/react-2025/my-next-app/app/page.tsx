import Link from "next/link";
import ProductCard from "./components/ProductCard/ProductCard";

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-start gap-[1.5rem] p-24">
      <h1 className="text-4xl font-bold">Page, Next.js!</h1>

      <Link href="/tailwind" className="text-blue-500 hover:underline">Tailwindcss</Link>
      <Link href="/users" className="text-blue-500 hover:underline">Users</Link>

      <ProductCard />
    </main>
  );
}
