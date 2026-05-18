import { defineConfig } from 'cypress';
import { env } from 'cypress/environments/environment';

export default defineConfig({
  projectId: 'nms-ng-ws',
  chromeWebSecurity: false,
  e2e: {
    baseUrl: env.testTarget,
    async setupNodeEvents(on: any, config: any) {
      require('cypress-mochawesome-reporter/plugin')(on);
      return config;
    },
    specPattern: ['cypress/integration/**/*.cy.{js,ts}'],

    reporter: 'cypress-mochawesome-reporter',
    reporterOptions: {
      reportDir: 'cypress/reports',
      charts: true,
      reportPageTitle: 'Activedash Test Suite',
      embeddedScreenshots: true,
      inlineAssets: true
    },
    video: true
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
