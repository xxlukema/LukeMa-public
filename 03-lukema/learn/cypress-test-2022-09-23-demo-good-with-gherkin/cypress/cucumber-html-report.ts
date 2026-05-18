const report = require('multiple-cucumber-html-reporter');

report.generate({
  jsonDir: 'cypress/reports/cucumber-json',
  reportPath: 'cypress/reports/cucumber-html-report',
  metadata: {
    browser: {
      name: 'chrome',
      version: '105'
    },
    device: 'Local Test Machine',
    platform: {
      name: 'windows',
      version: '10'
    },
    displayDuration: true,
    durationInMS: true,
    displayReportTime: true
  },
  customData: {
    title: 'Avtivedash Run Info',
    data: [
      { label: 'Project', value: 'Activedash' },
      { label: 'Execution Start Time', value: new Date() },
      { label: 'Execution End Time', value: new Date() }
    ]
  }
});
