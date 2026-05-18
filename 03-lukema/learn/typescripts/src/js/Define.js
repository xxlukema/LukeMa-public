

var x = x || 'Hello world.';

if (x) {
  // This line will hit:
  console.log('This line will hit for x: ' + x);
} else {
  x = 'This line will not hit for x.';
}

console.log(x);


var y = 1;

if (y) {
  // This line will hit:
  console.log('This line will hit for y: ' + y);
} else {
  y = 'This line will not hit for y.';
}

console.log(y);


var z = false;

if (z) {
  // This line will not hit:
  console.log('This line will not hit: ' + z);
} else {
  z = 'This line will hit for z.';
}

console.log(z);


try {
  // This will cause exception:
  if (neverDeclared) {
    console.log('This line will not hit for undeclared.');
  } else {
    console.log('This line will not hit for undeclared, either.');
  }
} catch (err) {
  console.log('This line will hit for undeclared: ' + err.message);
  console.log(err);
} finally {
  console.log('Finally.');
}


