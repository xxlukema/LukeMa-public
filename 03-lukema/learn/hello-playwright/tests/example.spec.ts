import { test, expect } from '@playwright/test';


test.describe('Playwright homepage', () => {
  test.beforeEach(async ({ page }) => {
    // Code to run before each test, e.g., navigating to a common URL.
    // await page.goto('https://playwright.dev/');
  
    // Use the baseURL set in playwright.config.ts, which uses `.env` variables.
    /**
     * `about:blank` is used as a fallback if no baseURL is defined.
     * This ensures that the tests do not fail due to a missing baseURL.
     */
    await page.goto('/')
  });

  // All tests in this describe block can share setup code.
  test('has title', async ({ page }) => {
    // Expect a title "to contain" a substring.
    await expect(page).toHaveTitle(/Playwright/);
  });

  test('get started link', async ({ page }) => {
    // Click the get started link.
    await page.getByRole('link', { name: 'Get started' }).click();

    // Expects page to have a heading with the name of Installation.
    await expect(page.getByRole('heading', { name: 'Installation' })).toBeVisible();
  });

  test('Record at cursor', async ({ page }) => {
    await page.goto('https://playwright.dev/');
    const page1Promise = page.waitForEvent('popup');
    await page.getByRole('link', { name: 'Star microsoft/playwright on' }).click();
    const page1 = await page1Promise;
    await page1.getByRole('cell', { name: 'chore: Remove duplicate' }).click();
  });
});
