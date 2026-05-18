
var x = new Object();

try {
  // This will print: object
  console.log('typeof x: ' + (typeof x));

  x.a = 1;
  console.log(x.a);
} catch (err) {
  console.log('Exception occured: ' + err.message);
} finally {
  console.log('Finally block.');
}










