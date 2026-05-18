import { defineConfig } from 'cypress';
import { env } from 'cypress/environments/environment';

const cucumber = require('cypress-cucumber-preprocessor').default;
const resolve = require('resolve');

export default defineConfig({
  projectId: 'nms-ng-ws',
  chromeWebSecurity: false,
  e2e: {
    /**
     * Trick 1: Use env.testTarget
     */
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

    /**
     * Trick 3: Use default value for `supportFile`
     */
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
    },
  },

  /**
   * Trick 2: Define env literals to be used in `/cypress/support/e2e.ts`, because `/cypress/support/e2e.ts`
   *          does not allow to use `import { env } from 'cypress/environments/environment';`
   */
  env: {
    kcRoot: env.devUrl,
    kcRealm: env.realm
  }
});
