
// var x;

/**
 * ReferenceError: x is not defined
 * Script will go to exception handling.
 **/
try {
  console.log('ReferenceError: x is not defined --> ' + x);
} catch (err) {
  console.log('Exception occured: ' + err.message);
  console.log('This is StackTrace: ', err);
} finally {
  console.log('Finally block.');
}










