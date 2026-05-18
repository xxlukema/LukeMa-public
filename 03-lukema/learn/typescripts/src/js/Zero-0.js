
var x = 0;

/**
 * Script will move forward.
 **/
if (x) { // Don't use this. Use if( x != null )
  console.log('Defined');
} else {
  // It WILL come here:
  console.log('Undefined');
}

// It will print: 0
console.log(x);

// false
console.log('x == null ? ' + (x == null));

// false
console.log('x === null ? ' + (x === null));

// false
console.log('x == \'null\' ? ' + (x == 'null'));

// typeof x: number
console.log('typeof x: ' + (typeof x));

