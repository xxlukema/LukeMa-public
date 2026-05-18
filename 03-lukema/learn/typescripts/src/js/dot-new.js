
/**
 * String literial cannot be assigned a member property.
 * Not this:
 *    var x = "a string literal";
 *    x.a = 1;
 * "new" operator has to be used to assigned a member property.
 *    var x = new String("a string literal");
 *    x.a = 1;
 *
 **/

// Not this:
var x = 'a string literal';

// OK is use "new" operator.
var x = new String('a string literal');

try {
  // This has to print: object to assign a new member property.
  console.log('typeof x: ' + (typeof x));

  // OK is use "new" operator.
  x.a = 1;
  console.log(x.a);
} catch (err) {
  console.log('Exception occured: ' + err.message);
} finally {
  console.log('Finally block.');
}










