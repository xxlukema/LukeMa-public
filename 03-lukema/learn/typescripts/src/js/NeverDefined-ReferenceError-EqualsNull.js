
/**
 * ReferenceError: x is not defined
 * Script will not move forward.
 **/
if (x == null) { // It will throw exception here and not move forward.
  console.log('It is null');
} else {
  console.log('It is not null');
}

// It will not come here.
if (x == undefined) { // It will throw exception here and not move forward.
  console.log('It is undefined');
} else {
  console.log('It is not undefined');
}

// It will not come here.
console.log(x);







