
var x = null;

/**
 * Script will move forward.
 **/
if (x) { // Don't use this. Use if( x != null )
  console.log('Defined');
} else {
  // It will come here:
  console.log('Undefined');
}

// It will print: undefined
console.log(x);

// true
console.log('x == null ? ' + (x == null));

// true
console.log('x === null ? ' + (x === null));

// false
console.log('x == \'null\' ? ' + (x == 'null'));

// typeof x: object
console.log('typeof x: ' + (typeof x));

