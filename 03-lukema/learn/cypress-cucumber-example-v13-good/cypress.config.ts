import { defineConfig } from 'cypress'

const cucumber = require('cypress-cucumber-preprocessor').default;
const resolve = require('resolve');

export default defineConfig({
  projectId: 'hello-cypress',
  chromeWebSecurity: false,

  e2e: {
    baseUrl: 'https://google.com',

    async setupNodeEvents(on: any, config: any) {
      const options = {
        typescript: resolve.sync('typescript', { baseDir: config.projectRoot }),
      };

      on('file:preprocessor', cucumber(options));

      return config;
    },

    // specPattern - default: 'cypress/e2e/**/*.cy.{js,jsx,ts,tsx}'
    // specPattern: ["cypress/integration/**/*.{feature,features}", "cypress/integration/**/*.cy.{js,jsx,ts,tsx}"]
    specPattern: ['cypress/integration/**/*.feature'],
    excludeSpecPattern: ['cypress/integration/**/*.cy.{js,jsx,ts,tsx}'],

    /**
     * supportFile - default: cypress/support/e2e.{js,jsx,ts,tsx}
     */
    // supportFile: 'cypress/support/e2e.ts'
  },

  component: {
    devServer: {
      framework: 'angular',
      bundler: 'webpack',
    },

    setupNodeEvents(on: any, config: any) {
      // component testing node events setup code
    },

    // specPattern: ['**/*.cy.{js,jsx,ts,tsx}']
    /**
     * default: cypress/support/component.js
     */
    // supportFile: 'cypress/support/component.ts'
  },
});

