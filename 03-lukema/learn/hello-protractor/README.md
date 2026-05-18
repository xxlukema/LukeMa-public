# `hello-protractor`

`hello-protractor` depends on `hello-libs-ng`. `hello-libs-ng` builds a sample library `my-conf-lib`. `hello-protractor` will
install and use the sample `my-conf-lib` as a learning practice of angular library.

`hello-protractor` requires `verdaccio` for `npm i --force`. If `npm i --force` is not needed, `verdaccio` section can be skipped.

## `verdaccio` must be running for `npm i --force` in `hello-protractor`

`verdaccio` must be running. `verdaccio` is an open source `npm` **repo server**

### 1. Install `verdaccio`: Run the following command in any directory

    # In any directory:
    npm i -g verdaccio
    # Or
    npm install --global verdaccio

### 2. Start "verdaccio" with this command in any directory

    # In any directory:
    verdaccio

### 3. Use "verdaccio"

[Local `verdaccio` URL]<http://localhost:4873/>

### 4. Add User to `verdaccio`

    npm adduser --registry http://localhost:4873/
    luke/luke/lukemal@yopmail.com

### 5. Login to `verdaccio`

    npm login --registry http://localhost:4873/
    luke/luke/lukemal@yopmail.com

### 6. (Skip This Step for Now) Publish to `verdaccio`

    # npm publish --registry http://localhost:4873/

## 7. Uninstall `@luke/my-conf-lib`. `@luke/my-conf-lib` must be uninstalled first in order to run `ncu`, `ncu -u` and `npm i --force`

    npm un @luke/my-conf-lib

## 8. Update All Packages

    # `@luke/my-conf-lib` must be uninstalled first in order to run `ncu`, `ncu -u` and `npm i --force`
    npm un @luke/my-conf-lib
    ncu
    ncu -u
    npm i --force
    # `verdaccio` must be running in order to run `npm run install_my_lib`
    verdaccio
    # login to `verdaccio` using credentials: luke/luke/lukemal@yopmail.com
    npm login --registry http://localhost:4873/
    npm run install_my_lib
    (now, verdaccio can be stopped)

## 9. Install `install_my_lib`

    # `verdaccio` must be running in order to run `npm run install_my_lib`
    verdaccio
    # login to `verdaccio` using credentials: luke/luke/lukemal@yopmail.com
    npm login --registry http://localhost:4873/
    npm run install_my_lib
    (now, verdaccio can be stopped)

## 10. That all about `verdaccio` and `@luke/my-conf-lib`

## Start Angular

    npm run start
    # Or
    ng serve

## Cypress

[Cypress MIT License - Free, Opensource, MIT License]<https://github.com/cypress-io/cypress/blob/develop/LICENSE>

[Cypress Sample Code Github]<https://github.com/cypress-io/> --- especially the recipes

[One Stop Reading]<https://docs.cypress.io/guides/core-concepts/introduction-to-cypress>

    npm install -D cypress --force
    npm install -D cypress-cucumber-preprocessor --force
    npm install -D @types/cypress-cucumber-preprocessor --force

    npm un cypress --force
    npm un cypress-cucumber-preprocessor --force

    npm un cypress-cucumber-preprocessor --force

    npm i -D --force @badeball/cypress-cucumber-preprocessor
    npm i -D --force @bahmutov/cypress-esbuild-preprocessor
    npm i -D --force @esbuild-plugins/node-modules-polyfill

    npm i -D --force @cypress/browserify-preprocessor

    # 1. Install cypress package
    npm install -D cypress --force
    # 2. Reinstall cypress 
    npx cypress install
    # 3. Run cypress with head
    npx cypress open
    # 4. Run cypress headlessly
    npx cypress run
    npx cypress run --record
    npx cypress run --record --parallel

    npx cypress run --record --key 13c1f7e4-ee1e-47a0-b68d-3cc7ab77ca3b

    https://on.cypress.io/configuration

## Cypress Bookmarks

[Cypress Cucumber]<https://www.browserstack.com/guide/how-to-run-cypress-cucumber-test>
[Cypress Configuration]<https://docs.cypress.io/guides/references/configuration>
[Cypress API]<https://docs.cypress.io/api/table-of-contents>
[Cypress Videos - Why Crpyess?]<https://docs.cypress.io/guides/overview/why-cypress#What-you-ll-learn>
[Cypress Sample Code Github]<https://github.com/cypress-io/>
[Cypress One Stop Into]<https://docs.cypress.io/guides/core-concepts/introduction-to-cypress>
[Cypress Real Wrold App]<https://github.com/cypress-io/cypress-realworld-app>

[Cypress One Stop Tutorial]<https://www.youtube.com/watch?v=avb-VDa3ZG4>

## `cypress-cucumber-preprocessor`

[cypress-cucumber-preprocessor]<https://www.npmjs.com/package/cypress-cucumber-preprocessor>

    # To run features boundle:
    cypress run --spec **/*.features

## Cucumber Expressions - `cucumber-expression`

[cucumber-expression]<https://github.com/cucumber/cucumber-expressions#readme>

[Cucumber Expressions Online]<https://cucumber.github.io/cucumber-expressions/>

    # command line:
    npx cypress run --spec cypress/integration/cucumber-test/login.feature //For single feature file
    # OR
    npx cypress run --spec cypress/integration/cucumber-test/*.feature //For all feature files

## `protractor`

N.B.: Run protractor tests with `--disableChecks`: `protractor` will not start `localhost:4200`. `--disableChecks` is to disable `webdriver` latest version check.

### 1. `npx protractor --disableChecks protractor.conf.js` will **not** start localhost:4200

    cd e2e
       npx protractor --disableChecks protractor.conf.js
       npx protractor --disableChecks protractor.conf.js
       npx protractor --disableChecks ./protractor.conf.js
       # --webdriver-update=false deprecated. use --disableChecks
       # npx protractor --webdriver-update=false ./protractor.conf.js

N.B.: There is still the following error, although the tests finally run:

[14260:19216:0606/130222.292:ERROR:device_event_log_impl.cc(214)] [13:02:22.291] USB: usb_device_handle_win.cc:1058 Failed to read descriptor
from node connection: A device attached to the system is not functioning. (0x1F)

### 2. `ng e2e` will start localhost:4200 for test by default

    ng e2e

### 3. Update `webdriver`

    npx webdriver-manager update --ignore_ssl --gecko false --standalone false --chrome
    # For old chrome 73.0.3683.68:
    npx webdriver-manager update --ignore_ssl --gecko false --standalone false --chrome --versions.chrome 89.0.4389.82

## `protractor` Tutorials

[Protractor Tutorials]<https://www.protractortest.org/#/tutorial>

## N.B.: `protractor-cucumber-framework` folders are always relative to `project-dir/src/e2e/`, regardless whether -

1. you run npm script from the command line, like `npm run it`,
2. or
3. you run `npx protractor ./protractor.conf.js` under `project-dir/src/e2e/`.

## N.B.: `cucumber-html-reporter`: `jsonFile: './test/cucumber-results.json'` or `jsonFile: './e2e/test/cucumber-results.json'`?

1. If you run npm script from the command line, like `npm run it`, the `jsonFile` is relative to the project
   root directory. Therefore, the correct value is: `jsonFile: './e2e/test/cucumber-results.json'`
2. If you run `npx protractor ./protractor.conf.js` under `project-dir/src/e2e/`, the `jsonFile` is relative
   to `project-dir/src/e2e/` directory. Therefore, the correct value is: `jsonFile: './test/cucumber-results.json'`
3. The second option is preferred. That is, run `npx protractor ./protractor.conf.js` under `project-dir/src/e2e/`,
   and use `jsonFile: './test/cucumber-results.json'`
4. If set `jsonFile: './e2e/test/cucumber-results.json'` for `cucumber-html-reporter`, then run `npm run it` is OK.
5. If set `jsonFile: './test/cucumber-results.json'` for `cucumber-html-reporter`, then run
   `npx protractor ./protractor.conf.js` under `project-dir/src/e2e/` is OK, while running `npm run it` will fail.

## N.B.: `protractor-multiple-cucumber-html-reporter-plugin`: `jsonOutputPath: './test'` or jsonOutputPath: `./e2e/test`?

1. N.B.: Always run `npx protractor protractor.conf.js` under `project-dir/src/e2e/` directory!
2. If you use a npm script from the command line, like for example `npm run it`, the `jsonOutputPath`
   is relative to the project root directory. Therefore, `jsonOutputPath: "./e2e/test"`.
3. If run `npx protractor ./protractor.conf.js` under `project-dir/src/e2e/` directory, the relative path will
   be from `project-dir/src/e2e/` directory. Therefore, `jsonOutputPath: "./test"`.
4. If you DONOT provide `jsonOutputPath`, it will generate a `json-output-folder` folder in the path that it
   defined the cucumberOpts.format.

## Always run `npx protractor ./protractor.conf.js` under `project-dir/src/e2e/`

- It had never been successful to run `npm run it` for `protractor-multiple-cucumber-html-reporter-plugin`.
- There is still the following error, although the tests finally run:
- [14260:19216:0606/130222.292:ERROR:device_event_log_impl.cc(214)] [13:02:22.291] USB: usb_device_handle_win.cc:1058 Failed to read descriptor from node
  connection: A device attached to the system is not functioning. (0x1F)

## Strange error

- There is still the following error, although the tests finally run:

    (This error can happen sporadically, even though with `browser.waitForAngularEnabled(false);`):
    [60148:82520:0424/154936.133:ERROR:device_event_log_impl.cc(214)] [15:49:36.133] USB: usb_device_handle
    win.cc:1054 Failed to read descriptor from node connection: A device attached to the system is not
    functioning. (0x1F)

## At the beginning of the steps

    async navigateTo(url: string): Promise<void> {
        /**
         * This is mandatory: `browser.waitForAngularEnabled(false);`
         *
         * Without `browser.waitForAngularEnabled(false);`, or set the value to true, then there will be the following two errors:
         * Error 1 of 2 (This error can happen sporadically, even though with `browser.waitForAngularEnabled(false);`):
         * [60148:82520:0424/154936.133:ERROR:device_event_log_impl.cc(214)] [15:49:36.133] USB: usb_device_handle
         * win.cc:1054 Failed to read descriptor from node connection: A device attached to the system is not functioning. (0x1F)
         * Error 2 of 2:
         * Some of the steps will fail to execute.
         */
        browser.waitForAngularEnabled(false);
        browser.get(url);
        /**
         * If not sleep here, there will be strange error of:
         *
         * [80972:47464:0424/154712.080:ERROR:device_event_log_impl.cc(214)] [15:47:12.080]
         * USB: usb_device_handle_win.cc:1054 Failed to read descriptor from node connection: A device attached to
         * the system is not functioning. (0x1F).
         */
        await browser.sleep(1_000);
    }

## At the end of the steps

    /**
     * Using `await browser.sleep(200);` instead of `setTimeout(() => {}, 200)`, so that the code
     * has the look and feel of synchronous, while it is actually asynchronous.
     */
    async closeAndQuit(): Promise<void> {
        // Sleep to avoid "UnhandledPromiseRejectionWarning: NoSuchSessionError: invalid session id"
        await browser.sleep(200);
        // 1. close first.
        browser.close();
        // 2. then quit.
        browser.quit();
        /*
        setTimeout(() => {
            // 1. close first.
            browser.close();
            // 2. then quit.
            browser.quit();
        }, 200);
        */
    }

## `close` then `quit`

    // 1. close first.
    browser.close();
    // 2. then quit.
    browser.quit();

## To use specific chromedriver version

1. `npx webdriver-manager update --ignore_ssl --gecko false --standalone false --chrome`
2. `npx webdriver-manager update --ignore_ssl --gecko false --standalone false --chrome --versions.chrome 89.0.4389.82`
3. `npx protractor --disableChecks ./protractor.conf.js`
4. `'--disableChecks'` and `'chromeDriver'` can replace each other.
   `chromeDriver` and `../node_modules/protractor/node_modules/webdriver-manager/selenium/update-config.json` must point
   to the same chromedriver.
5. If relative path is used, it is relative to the protractor.config.js file folder.

## Fix of "SyntaxError: Cannot use import statement outside a module"

    onPrepare() {
        /**
         * The following three lines are very important! Without these three line, There will be error like this:
         *    import { browser, by, element, ElementFinder } from 'protractor';
         *    ^^^^^^
         *    SyntaxError: Cannot use import statement outside a module
         */
        require('ts-node').register({
            project: require('path').join(__dirname, './tsconfig.json')
        });
    },

## Jasmine vs Karma vs Protractor

Protractor is a Selenium wrapper, it's not a testing framework itself. Protractor includes Jasmine, see conf.js
[Protractor Get Started Page]<https://www.protractortest.org/#/tutorial>

Jasmine is a behavior-driven development framework for testing JavaScript code. It does not depend on any other JavaScript frameworks.
It does not require a DOM. And it has a clean, obvious syntax so that you can easily write tests.

Karma is essentially a tool for testing which spawns a web server that executes source code against test code for each of the browsers
connected. The results of each test against each browser are examined and displayed via the command line to the developer such that
they can see which browsers and tests passed or failed.

Jasmine and Karma are usually used together to perform Unit testing or integration testing.

Protractor is an end-to-end test framework for Angular and AngularJS applications. Protractor runs tests against your application running
in a real browser, interacting with it as a user would without depending on other tools for performing the same.

Protractor and Karma should not be used together; instead they provide separate systems for running tests. Protractor and Karma cover
different aspects of testing - Karma is intended mostly for unit tests, while Protractor should be used for end to end testing.

- Protractor - For end to end tests, with native event driving and flexibility of webdriver.

- Karma - For fast execution and autowatching of files.

npm i -D --force @types/jasmine
npm i -D --force jasmine-core
npm i -D --force jasmine-marbles
npm i -D --force jasmine-spec-reporter
npm i -D --force karma
npm i -D --force karma-chrome-launcher
npm i -D --force karma-coverage
npm i -D --force karma-jasmine
npm i -D --force karma-jasmine-html-reporter
