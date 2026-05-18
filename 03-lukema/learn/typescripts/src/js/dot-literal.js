
var x = 'a string literal';

try {
  // This will print: boolean
  console.log('typeof x: ' + (typeof x));

  // This assignment is not accepted:
  x.a = 1;
  console.log(x.a);
} catch (err) {
  console.log('Exception occured: ' + err.message);
} finally {
  console.log('Finally block.');
}










