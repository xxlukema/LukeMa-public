// Protractor configuration file, see link for more information
// https://github.com/angular/protractor/blob/master/lib/config.ts

'use strict';

const { browser } = require("protractor");

/**
 * @type { import("protractor").Config }
 */
exports.config = {
    allScriptsTimeout: 11000,
    specs: [
        './src/features/*.feature'
    ],
    capabilities: {
        /**
         * To use specific chromedriver version:
         *
         * 1. `npx webdriver-manager update --ignore_ssl --gecko false --standalone false --chrome`
         *    Or
         *    `npx webdriver-manager update --ignore_ssl --gecko false --standalone false --chrome --versions.chrome 89.0.4389.82`
         * 2. `npx protractor --disableChecks ./protractor.conf.js`
         * 3. `'--disableChecks'` and `'chromeDriver'` can replace each other.
         *    `chromeDriver` and `../node_modules/protractor/node_modules/webdriver-manager/selenium/update-config.json` must point
         *    to the same chromedriver.
         * 4. If use relative path, it relatived to the protractor.config.js file folder.
         */
        // chromeDriver: '../node_modules/protractor/node_modules/webdriver-manager/selenium/chromedriver_90.0.4430.24.exe',
        browserName: 'chrome',
        chromeOptions: {
            args: [
                "no-sandbox",
                "disable-infobars",
                "disable-extensions",
                "enable-gpu",
                "test-type=ui",
                "ignore-ssl-errors",
                "ignore-certificate-errors",
                "allow-insecure-localhost",
                "disable-web-security",
                "allow-running-insecure-content",
                // "js-flags=expose-gc",
                // "enable-precise-memory-info",
                "disable-popup-bloacking",
                "disable-dev-shm-usage",
                "window-size=1400,750",
                "disable-setuid-sandbox",
                "remote-debugging-port=9222",
                "disable-translate",
                "disable-notifications",
                "disable-session-crashed-bubble",
                "disable-restore-session-state",
                // "incognito",
                // "noerrdialogs",
                // "enable-automation"
                "'excludeSwitches': ['enable-automation', 'enable-logging']",
                // "'experimentalOption': {'excludeSwitches': ['enable-automation', 'enable-logging']}"
            ]
        }
    },
    directConnect: true,
    SELENIUM_PROMISE_MANAGER: false,
    baseUrl: 'http://localhost:4200/',
    framework: 'custom',
    // frameworkPath: require.resolve('protractor-cucumber-framework'),
    frameworkPath: require.resolve('serenity-js'),
    cucumberOpts: {
        /**
         * N.B.: 'protractor-cucumber-framework' folders are under project-dir/src/e2e/, regardless whether
         * you run npm script from the command line, like 'npm run it',
         * Or
         * you run 'npx protractor ./protractor.conf.js' under project-dir/src/e2e/.
         */
        // require step definitions
        require: [
            './src/hooks/**/*.ts',
            './src/steps/**/*.ts'
        ],
        format: "json:./build/cucumber-results.json",
        // resultJsonOutputFile: "./build/cucumber-results.json",
        tags: "@UserLoginFeature"
    },
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
    //Create html report
    onComplete: () => {
        const reporter = require('cucumber-html-reporter');
        const options = {
            theme: "bootstrap",
            /**
             * https://www.npmjs.com/package/cucumber-html-reporter
             *
             * N.B.: jsonFile takes precedence over jsonDir. We recommend to use either jsonFile or jsonDir option.
             *
             * Final Report (Open use browser):
             * D:\03-lukema\LukeMa\03-lukema\learn\hello-protractor\e2e\build\report\cucumber-html-report.html
             */
            jsonFile: './build/cucumber-results.json',
            // jsonDir: './build',
            output: './build/report/cucumber-html-report.html',
            reportSuiteAsScenarios: true,
            launchReport: true,
            brandTitle: 'My Learn Project',
            metadata: {
                "Test Environment": "STAGING",
                "Browser": "Chrome",
                "Parallel": "Scenarios",
                "Executed": "Remote",
                "App Version": "0.3.2",
                // "Browser": "Chrome 54.0.2840.98",
                "Platform": "Windows 10",
            },
        };
        reporter.generate(options, () => { });
    },
    plugins: [{
        /**
         * https://www.npmjs.com/package/protractor-multiple-cucumber-html-reporter-plugin
         * jsonOutputPath: default:  json-output-folder
         *
         * N.B.: If you use a npm script from the command line, like for example 'npm run generate-report' the 'jsonOutputPath'
         * will be relative from the path where the script is executed.
         * Executing it from the root of your project will also search for the 'jsonOutputPath' from the root of you project.
         *
         * If you DONOT provide this it will generate a json-output-folder folder in the path that it defined the
         * cucumberOpts.format.
         *
         * Final Report (Open use browser):
         * D:\03-lukema\LukeMa\03-lukema\learn\hello-protractor\e2e\build\report\index.html
         */
        package: 'protractor-multiple-cucumber-html-reporter-plugin',
        options: {
            // jsonOutputPath: './e2e/build',
            // read the options part for more options
            automaticallyGenerateReport: true,
            removeExistingJsonReportFile: true,
            pageTitle: "Project Report",
            pageFooter: "<div><p>Protractor test with cucumber</p></div>",
            customData: {
                title: 'Protractor Test Cucucmber Report',
                data: [
                    { label: 'Project', value: 'Protractor with Cucumber test' },
                    { label: 'Created By', value: 'Luke Ma' }
                ]
            }
        }
    }]
};
