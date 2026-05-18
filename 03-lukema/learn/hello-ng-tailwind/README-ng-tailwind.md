# ng + tailwind

    ng new hello-ng-tailwind --style css
    cd hello-ng-tailwind

    npm install tailwindcss @tailwindcss/postcss postcss --force

## `.postcssrc.json`

    {
      "plugins": {
        "@tailwindcss/postcss": {}
      }
    }

## `style.css`

    @import "tailwindcss";

## `app.componnent.html`

    <h1 class="text-3xl font-bold underline">
      Hello world!
    </h1>
