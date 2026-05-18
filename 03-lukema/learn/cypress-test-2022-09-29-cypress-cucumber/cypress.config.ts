import { defineConfig } from 'cypress';
import { env } from 'cypress/environments/environment';

const cucumber = require('cypress-cucumber-preprocessor').default;
const resolve = require('resolve');

export default defineConfig({
  projectId: 'ybnoap',
  chromeWebSecurity: false,
  e2e: {
    baseUrl: env.testTarget,
    async setupNodeEvents(on: any, config: any) {
      const options = {
        typescript: resolve.sync('typescript', { baseDir: config.projectRoot }),
      };
      on('file:preprocessor', cucumber(options));
      return config;
    },
    specPattern: ['cypress/integration/**/*.feature'],
    excludeSpecPattern: ['cypress/integration/**/*.cy.{js,ts}'],
  },
  component: {
    devServer: {
      framework: 'angular',
      bundler: 'webpack',
    },
    setupNodeEvents(on: any, config: any) {
    },
  },

  env: {
    kcRoot: env.devUrl,
    kcRealm: env.realm
  }
});
