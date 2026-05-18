
const browserify = require('@cypress/browserify-preprocessor');
const cucumber = require('@badeball/cypress-cucumber-preprocessor').default;
const resolve = require('resolve');
const addCucumberPreprocessorPlugin = require('@badeball/cypress-cucumber-preprocessor').addCucumberPreprocessorPlugin

module.exports = async (on, config) => {
  const options = {
    ...browserify.defaultOptions,
    typescript: resolve.sync('typescript', { baseDir: config.projectRoot }),
  };

  on('file:preprocessor', cucumber(options));

  await addCucumberPreprocessorPlugin(on, config)
};
