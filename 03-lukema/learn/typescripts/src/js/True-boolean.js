
var x = true;

/**
 * Script will move forward.
 **/
if (x) { // Don't use this. Use if( x != null )
  // It will come here.
  console.log('Defined');
} else {
  // It will not come here:
  console.log('Undefined');
}

// It will print: true
console.log(x);

// false
console.log('x == null ? ' + (x == null));

// false
console.log('x === null ? ' + (x === null));

// false
console.log('x == \'null\' ? ' + (x == 'null'));

// typeof x: boolean
console.log('typeof x: ' + (typeof x));

