const { exec } = require('child_process');
const replace = require('replace-in-file');
const pkg = require('./package.json');
const os = require('os');
const getBranch = () => new Promise((resolve, reject) => {
  return exec('git rev-parse --abbrev-ref HEAD', (err, stdout, stderr) => {
    if (err) {
      reject(`getBranch Error: ${err}`);
    } else if (typeof stdout === 'string') {
      resolve(stdout.trim());
    }
  });
});
const doReplace = async () => {
  const branch = (await getBranch()).split('/')[1];
  const timezone = 'America/Los_Angeles';
  const buildVersion = pkg.version + ' ' + new Date().toLocaleString('en-US', {
    timeZone: timezone
  }) + ' ' + timezone + ' ' + os.hostname;
  const fieldBuildVersionCommon = {
    files: 'projects/common/src/environments/environment.prod.ts',
    from: /{BUILD_VERSION}/g,
    to: buildVersion,
    allowEmptyPaths: false,
  };
  const fieldBuildVersion5g = {
    files: 'projects/5g/src/environments/environment.prod.ts',
    from: /{BUILD_VERSION}/g,
    to: buildVersion,
    allowEmptyPaths: false,
  };
  const fieldBuildVersionNms = {
    files: 'projects/nms/src/environments/environment.prod.ts',
    from: /{BUILD_VERSION}/g,
    to: buildVersion,
    allowEmptyPaths: false,
  };
  const fieldBranchNameCommon = {
    files: 'projects/common/src/environments/environment.prod.ts',
    from: /{BRANCH_NAME}/g,
    to: branch,
    allowEmptyPaths: false,
  };
  const fieldBranchName5g = {
    files: 'projects/5g/src/environments/environment.prod.ts',
    from: /{BRANCH_NAME}/g,
    to: branch,
    allowEmptyPaths: false,
  };
  const fieldBranchNameNms = {
    files: 'projects/nms/src/environments/environment.prod.ts',
    from: /{BRANCH_NAME}/g,
    to: branch,
    allowEmptyPaths: false,
  };
  try {
    replace.sync(fieldBuildVersionCommon);
    replace.sync(fieldBranchNameCommon);
    replace.sync(fieldBranchName5g);
    replace.sync(fieldBuildVersion5g);
    replace.sync(fieldBranchNameNms);
    replace.sync(fieldBuildVersionNms);
    console.log('Build:', branch, buildVersion);
  } catch (error) {
    console.error('Error occurred:', error);
  }
};
doReplace();
