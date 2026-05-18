# tailwind

    npx create-next-app@latest my-project --typescript --eslint --app

    npm i -D tailwindcss @tailwindcss/postcss postcss
    

## DaisyUI can't find type module

Create `globals.d.td` file in the project root. Add `declare module 'daisyui'` to that file.

    # `project_root/postcss.config.mjs`
    const config = {
      plugins: {
        "@tailwindcss/postcss": {},
      },
    };
    export default config;

    # `project_root/globals.d.ts`:
    declare module 'daisyui'

## vscode extension: `Tailwind CSS Intellisense` (by Tailwind Labs)

## Telling vscode to use `Tailwind CSS Intellisense` (by Tailwind Labs) to open `.css` files

    # `project_root/.vscode/settings.json`:
    {
      "files.associations": {
        "*.css": "tailwindcss",
        "*.scss": "tailwindcss",
      }
    }

## Not working

- mt-??
- space-x-6 <===== use gap-6
- Mobile first: It uses mobile screen size first (smaller screen)
- Don't use `sm:` to target mobile devices
- Use unprefixed utilities to target mobile, and override them with larger breakpoints.
  Example:

      <div className='text-center sm:text-left'></div>

- @theme

    @theme {
      --breakpoint-xs: 30rem;
      --breakpoint-2xl: 100rem;
      --breakpoint-3xl: 120rem;
    }

- Dark mode: 'bg-white dark:bg-black text-black dark:text-white'
- Toggle dark mode button
- test-[#973f29] bg-[#973f29] p-[16] text-[1.5rem]
- config: In `globales.css`, add `@theme` and configure it.
- Why thes are not working?

    @custom-variant dark (&:where(.dark, .dark *));

    @theme {
      --color-chestnut: #a0522d;
    }

## Dark mode

70% of software engineers opt for Dark Theme IDEs.

## `@layer base` `@layer components` `@layer utility`

    @layer base {
      h1 {
        font-size: var(--test-2xl);
      }
    
      h2 {
        font-size: var(--text-xl);
      }

      h3 {
        @apply text-base font-medium dark:text-white;
      }
    }

    <h3></h3>  <!-- no need to specify class name -->


    @layer components {
      .card {
        background-color: var(--color-white);
        boder-radius: var(--round-lg);
        box-shadow: (var--shadow-xl);
      }
    }

    @layer utility content-auto {
      content-visibility: auto;
    }

    <div class='content-auto'>
      ---
    </div>

    .select2-dropdown {
      @apply rounded-b-lg shadow-md;
    }
    
    .select2-search {
      @apply rounded norder border-grey-300;
    }
    
    .select2-results__group {
      @apply text-lg font-bold text-grey-900;
    }

    @layer components {
      .card {
        @apply m-10 rounded-lg db-white ring-1 ring-slate-900/5 dark:bg-black;
      }
    }

    <div class='card'></div>

## Directives

## Sharsian

- button
- accordian
- and more

## Tricks

- Accent: Change checkbox color --- class='accent-pin-500'
- Fluid texts: class='text-[min(10w, 70px)]'
- File: <!-- <input type='file' class='file:rounded-full file:border-2'> -->
- Highlight: class='selection:bg-green-400 select:text-white'
- Cursor color: <!-- <textarea class='w-full caret-pink-500 text-white'> -->
- `before:` and `active:` and more
- [Become a Top 1% Next.js Developer]<https://jsm.dev/tailwindv4-nextjs>
