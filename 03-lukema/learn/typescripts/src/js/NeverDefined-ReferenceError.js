
// var x;

/**
 * ReferenceError: x is not defined
 * Script will not move forward.
 **/
if (x) { // It will throw exception here and not move forward.
  console.log('Defined');
} else {
  console.log('Undefined');
}

// It will not come here.
console.log(x);







