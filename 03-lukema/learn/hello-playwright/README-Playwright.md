# Playwright

[Demo UI]<https://demo.playwright.dev/todomvc/#/>

## Extensiom

    Playwright Test for VSCode (by Microsoft)

## Install drivers

    Shift + Ctrl + p :: Install Playwright --- This will install drivers (Chrome, Firefox, Wentik)

## `.env`

    npm i dotenv

### `.env` file

    BASE_URL=https://staging.example.com
    USERNAME=testuser
    PASSWORD=secret

### `playwright.config.ts`

    import { defineConfig } from '@playwright/test';
    import dotenv from 'dotenv';
    import path from 'path';
    
    // Read from ".env" file
    dotenv.config({ path: path.resolve(__dirname, '.env') });
    
    export default defineConfig({
      use: {
        // Access variables using process.env.
        baseURL: process.env.BASE_URL || 'http://localhost:3000',
      },
      // ... other configurations
    });

### `example.spec.ts`

    import { test, expect } from '@playwright/test';
    
    test('example test', async ({ page }) => {
      // Use the baseURL defined in the config file, which uses the .env variable
      await page.goto('/');
    
      // Access other variables directly
      console.log(`API Key: ${process.env.API_KEY}`);
      // ...
    });

### Steps to use "Record at cursor"

- Open the **Testing Sidebar**: Navigate to the Test Explorer tab in the VS Code sidebar (the icon looks like a beaker or a **test tube**).
- Ensure "Show browser" is checked: In the Playwright section of the Test Explorer toolbar, make sure the "Show browser" checkbox
  (or equivalent setting in the extension) is checked. This ensures the browser window remains open after test execution, allowing
  you to continue recording from its current state.
- Run the test to the desired point: Place your cursor in the existing test file where you want to start recording,
  then run the test up to that point. This will bring the browser to the correct page and state.
- Click "Record at cursor": Once the test has run and the browser window is open at the correct state, click the "Record at cursor"
  button in the Testing sidebar's toolbar.
- Perform actions in the browser: A browser window will open (or the existing one will be used). Perform the actions you want to record,
  such as clicking elements or entering text. Playwright will automatically generate the corresponding code snippets at your cursor's
  position in the VS Code editor.
- Stop recording: The recording will stop when you click the stop button in the toolbar, or when the test naturally ends.

#### Troubleshot for "Record at cursor"  

- Enable "Show browser": In the VS Code testing sidebar, find and check the "Show browser" option in the extension's settings.
  This keeps the browser open after a test run.
- Run the test first: Run the existing test you want to extend with "Show browser" enabled. The test will run and stop at the breakpoint
  or the end of the current code, leaving the browser open on the correct page.
- Click "Record at cursor" afterward: With the browser still open and your cursor at the desired line in your test file, click
  the "Record at cursor" button in the Testing sidebar. The extension will attach to the open browser instance and begin recording
  new actions at that position.
- Ensure the cursor is on a new line inside a test block: Sometimes, the feature fails if the cursor is not placed on a new, empty line
  within the relevant `test()` or `test.describe()` block where you want the new code generated.

#### Common Issues and Solutions

- Browser closes immediately: This is usually because "Show browser" is not enabled. The browser needs to persist after the initial
  run for "Record at cursor" to attach to it.
- `about:blank` page opens: This happens when Playwright doesn't know which URL to navigate to, often because the necessary `beforeEach`
  hooks or `baseURL` from the config weren't executed. Running the test first, as described above, ensures the correct context and URL are loaded.
- Button is greyed out or inactive:
  - Ensure the cursor is within a valid test file and a test block.
  - If you are already in an active recording session, the button might not function as expected; you can simply delete the lines in the
    editor and continue recording without clicking "Record at cursor" again.
- Extension or VS Code glitch: If all else fails, a simple restart of VS Code (using the "Reload Window" command from the Command Palette)
  can often resolve minor extension issues.

## Commands

    # 'allure' is not recognized as an internal or external command
    npm install -g allure-commandline --save-dev

    npx allure generate allure-results --clean -o allure-report

    npx playwright test manage-sys-ids
    npx playwright test -g "API Auth Client ID Info Test"
    npx playwright test --list
    npx playwright test --project=chromium
    npx playwright test --help
    npx playwright test --only-changed
    npx playwright test --pass-with-no-tests
    npx playwright test --quiet
    npx playwright test --trace on

    npx playwright install --dry-run
    npx playwright install chromium

    npx playwright show-report
    npx playwright show-report --port 8989
