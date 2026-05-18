
/**
 * Assign use "new" operator
 * or
 * use (function () {...})();
 */
const MyFunc = (function () {

  let value = 0;

  const inc = function () {
    value++;
    console.log('MyFunc.inc() ' + value);
  };

  const log = function () {
    console.log('MyFunc.log() ' + value);
  };

  return {
    log2: log,
    add: inc
  };

  /**
     * Function executed.
     */
})();

MyFunc.log2();
/**
 * inc() is not visible to public. inc() is exposed as add()
 * log() is not visible to public. log() is exposed as log2()
 * */
try {
  MyFunc.inc();
} catch (err) {
  console.log(err.message);
}
MyFunc.add();
MyFunc.log2();

/**
 * Assign use "new" operator
 * or
 * use (function () {...})();
 */
const MyFunc2 = new function () {

  let value = 0;

  const inc = function () {
    value++;
    console.log('MyFunc2.inc() ' + value);
  };

  const log = function () {
    console.log('MyFunc2.log() ' + value);
  };

  return {
    log: log,
    add: inc
  };

  /**
    * Function executed.
    */
}();

/**
 * Before run that function:
 *
 * MyFunc2.log() is not a function
 * */
try {
  MyFunc2.log();
} catch (err) {
  console.log(err.message);
}

/**
 * Before run that function:
 *
 * MyFunc2.log() is a function
 * */
// MyFunc2();

MyFunc2.log();
MyFunc2.add();

