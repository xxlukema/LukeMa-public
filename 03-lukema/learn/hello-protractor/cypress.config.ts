import { defineConfig } from 'cypress'

const browserify = require('@cypress/browserify-preprocessor');
// const cucumber = require('@badeball/cypress-cucumber-preprocessor').default;
const resolve = require('resolve');

//For Cucumber Integration
const createEsbuildPlugin = require('@badeball/cypress-cucumber-preprocessor/esbuild').createEsbuildPlugin
const createBundler = require('@bahmutov/cypress-esbuild-preprocessor')
const nodePolyfills = require('@esbuild-plugins/node-modules-polyfill').NodeModulesPolyfillPlugin
// const addCucumberPreprocessorPlugin = require('@badeball/cypress-cucumber-preprocessor').addCucumberPreprocessorPlugin

/*
module.exports = async (on: any, config: any) => {
  await addCucumberPreprocessorPlugin(on, config) // to allow json to be produced
  // To use esBuild for the bundler when preprocessing
  on(
    'file:preprocessor',
    createBundler({
      plugins: [nodePolyfills(), createEsbuildPlugin(config)],
    })
  )
  return config
}
*/

/*
module.exports = (on: any, config: any) => {
  const options = {
    ...browserify.defaultOptions,
    typescript: resolve.sync('typescript', { baseDir: config.projectRoot }),
  };

  on('file:preprocessor', cucumber(options));
};
*/

export default defineConfig({

  projectId: 'hello-protractor',
  chromeWebSecurity: false,

  e2e: {
    // baseUrl: 'http://localhost:1234',
    // baseUrl: 'https://www.saucedemo.com',

    baseUrl: 'https://google.com',

    async setupNodeEvents(on, config) {
      // implement node event listeners here

      const options = {
        ...browserify.defaultOptions,
        typescript: resolve.sync('typescript', { baseDir: config.projectRoot }),
      };

      // on('file:preprocessor', cucumber(options));

      // await addCucumberPreprocessorPlugin(on, config)

      on(
        'file:preprocessor',
        createBundler({
          plugins: [nodePolyfills(), createEsbuildPlugin(config)],
        })
      );

    },

    // specPattern: "cypress/integration/**/*.{feature,features}",
    // specPattern: "cypress/integration/**/*.cy.ts",

    // specPattern: ["cypress/integration/**/*.{feature,features}", "cypress/integration/**/*.cy.{js,jsx,ts,tsx}"]
    specPattern: ["cypress/integration/saucedemo/**/*.{feature,features}"]
  },

  component: {
    devServer: {
      framework: "angular",
      bundler: "webpack",
    },

    setupNodeEvents(on, config) {
      // component testing node events setup code
    },

    // specPattern: "**/*.cy.ts",
  },

})
