
exports.config = {
  framework: 'jasmine',
  directConnect: false,
  seleniumAddress: 'http://localhost:4444/wd/hub',
  specs: ['calculator-test.spec.js'],
  capabilities: {
    // browserName: 'firefox'
    browserName: 'chrome'
  },

  /*
  multiCapabilities: [{
    browserName: 'firefox'
  }, {
    browserName: 'chrome'
  }],
  */

  // Options to be passed to Jasmine-node.
  jasmineNodeOpts: {
    showColors: true, // Use colors in the command line report.
  }
}


