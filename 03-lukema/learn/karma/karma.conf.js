// Karma configuration
// Generated on Tue Nov 14 2017 16:34:09 GMT-0500 (Eastern Standard Time)

module.exports = function (config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],


    // list of files / patterns to load in the browser
    files: [
      /**
       * This pattern section is manditory. And 'base/' is manditory.
       * 
       *     jasmine.getFixtures().fixturesPath = 'base/test';
       *     loadFixtures('calculator.fixture.html');
       *     // jasmine.getFixtures().load('calculator.fixture.html');
       *     // console.log('Is jasmine-fixtures loaded? ' + $('#jasmine-fixtures'));
       */
      {
        pattern: 'test/**/*.html',
        watched: true,
        served: true,
        included: false
      },
      'node_modules/jquery/dist/jquery.js',
      'node_modules/jasmine-jquery/lib/jasmine-jquery.js',
      'js/*.js',
      'test/**/*.spec.js'
    ],


    // list of files to exclude
    exclude: [
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
    },


    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress', 'kjhtml'],
    // reporters: ['progress'],

    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: [
      'Chrome'
      // 'Chrome_without_security'
    ],
    // browsers: ['PhantomJS'],

    /*
    customLaunchers: {
      Chrome_without_security: {
        base: 'Chrome',
        flags: [
          //'--disable-web-security', 
          '--allow-file-access-from-files'
        ]
      },

      Chrome: {
        flags: [
          '--allow-file-access-from-files'
        ]
      }
    },
    */


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false,

    // Concurrency level
    // how many browser should be started simultaneous
    concurrency: Infinity
  })
}
