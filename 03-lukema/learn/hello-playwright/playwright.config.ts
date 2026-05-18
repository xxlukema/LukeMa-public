import { defineConfig, devices } from '@playwright/test';
import dotenv from 'dotenv';
import path from 'node:path';

// Read from ".env" file
dotenv.config({ path: path.resolve(__dirname, '.env') });

/**
 * See https://playwright.dev/docs/test-configuration.
 */
export default defineConfig({
  // testDir: './tests',
  /* Run tests in files in parallel */
  fullyParallel: true,
  /* Fail the build on CI if you accidentally left test.only in the source code. */
  forbidOnly: !!process.env.CI,
  /* Retry on CI only */
  retries: process.env.CI ? 2 : 0,
  /* Opt out of parallel tests on CI. */
  workers: process.env.CI ? 1 : undefined,
  /* Reporter to use. See https://playwright.dev/docs/test-reporters */
  reporter: 'html',
  timeout: 30 * 1_000,
  expect: {
    timeout: 5 * 1_000,
  },
  /* Shared settings for all the projects below. See https://playwright.dev/docs/api/class-testoptions. */
  use: {
    /* Base URL to use in actions like `await page.goto('')`. */
    baseURL: process.env.baseUrl,

    /* Collect trace when retrying the failed test. See https://playwright.dev/docs/trace-viewer */
    trace: 'on-first-retry',

    screenshot: 'only-on-failure',
    video: 'retain-on-failure',
    actionTimeout: 10 * 1_000,
    navigationTimeout: 20 * 1_000,
    launchOptions: {
      args: ['--start-maximized', "--incognito", "--new-window"]
    },
  },

  /* Configure projects for major browsers */
  projects: [
    {
      name: 'Example Test',
      testDir: './tests',
      testMatch: [/example\.spec\.ts/],
      use: {
        baseURL: process.env.BASE_URL ?? 'https://playwright.dev',
        channel: 'chrome',
        viewport: null,
        launchOptions: {
          args: ['--start-maximized', "--incognito", "--new-window"],
        },
      },
    },
    {
      name: 'Playwright Todo Test',
      testDir: './tests',
      testMatch: [/playwright-todo\.spec\.ts/],
      use: {
        baseURL: process.env.BASE_URL ?? 'https://playwright.dev',
        channel: 'chrome',
        viewport: null,
        launchOptions: {
          args: ['--start-maximized', "--incognito", "--new-window"],
        },
      },
    }


    /**
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },

    {
      name: 'firefox',
      use: { ...devices['Desktop Firefox'] },
    },

    {
      name: 'webkit',
      use: { ...devices['Desktop Safari'] },
    },
    */

    /* Test against mobile viewports. */
    // {
    //   name: 'Mobile Chrome',
    //   use: { ...devices['Pixel 5'] },
    // },
    // {
    //   name: 'Mobile Safari',
    //   use: { ...devices['iPhone 12'] },
    // },

    /* Test against branded browsers. */
    // {
    //   name: 'Microsoft Edge',
    //   use: { ...devices['Desktop Edge'], channel: 'msedge' },
    // },
    // {
    //   name: 'Google Chrome',
    //   use: { ...devices['Desktop Chrome'], channel: 'chrome' },
    // },
  ],

  /* Run your local dev server before starting the tests */
  // webServer: {
  //   command: 'npm run start',
  //   url: 'http://localhost:3000',
  //   reuseExistingServer: !process.env.CI,
  // },
});
