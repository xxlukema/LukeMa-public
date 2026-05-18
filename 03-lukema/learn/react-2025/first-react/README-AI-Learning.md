# AI Cert Learning

[Course by Tom Chant]<https://www.coursera.org/learn/intro-to-ai-engineering/ungradedWidget/v5XFo/welcome-to-the-ai-engineer-specialization>

One week free trial. Start date: Friday March 14, 2025
Billing starts: March 22, 2025 (H-E-B card)

## Sites

### Poligon.io <luke.ma.2023@gmail.com>/Cfg-

[Polygon.io]<https://polygon.io/>

    POLYGON_API_KEY: LRhv4blNxQL2WbLCshP3WhIfFNNunzoR

## Installs

    npm i openai
    npm i -D tsx

    # tsx
    npx tsx deepseek/hello-deepseek.ts

## (skip. dotenv does not work for React) `npm install dotenv`

    npm install dotenv

    # .env file
    MY_SECRET=your_secret_value

    # ts file
    import * as dotenv from 'dotenv';
    dotenv.config();
    
    const mySecret = process.env.MY_SECRET;
    
    if (mySecret) {
      // Use the secret
      console.log('Secret:', mySecret);
    } else {
      console.error('Secret not found in environment variables');
    }

## Config `vite.config.ts` to load secrets from .env file

**Important**: place `.env` file into `.gitignore`

    import react from '@vitejs/plugin-react'
    import path from 'path'
    import { defineConfig, loadEnv } from 'vite'
    
    // https://vite.dev/config/
    export default defineConfig(({ mode }) => {
      const env = loadEnv(mode, process.cwd(), '');
      return {
        define: {
          'process.env.OPENAI_API_KEY': JSON.stringify(env.OPENAI_API_KEY)
        },
        plugins: [react()],
        build: {
          sourcemap: true,
        },
        resolve: {
          alias: {
            '@': path.resolve(__dirname, './src'),
          },
        },
      }
    })
