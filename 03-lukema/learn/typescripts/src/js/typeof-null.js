
// var x = null;

/**
 * typeof null --> object
 * typeof undefined --> undefined
 *
 **/

try {
  console.log('typeof x: ' + (typeof x));

  if (typeof x === 'undefined') {
    // It will come here:
    console.log('typeof x == \'undefined\': ' + (typeof x === 'undefined'));
  }

  if (typeof x === 'undefined') {
    // It will come here:
    console.log('typeof x === \'undefined\': ' + (typeof x === 'undefined'));
  }
} catch (err) {
  console.log('Exception occured: ' + err.message);
  console.log('This is StackTrace: ', err);
} finally {
  console.log('Finally block.');
}










