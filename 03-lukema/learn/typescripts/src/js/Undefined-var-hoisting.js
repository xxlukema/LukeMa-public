
/**
 * This can be at the end of this file:
 * var x;
 * It does not have to be declared before using it.
 * One can use a variable first and declare it later.
 * This is called variable hoisting.
 * However, variables that are hoisted will return a value of undefined.
 * So even if you declare and initialize after you use or refer
 * to this variable, it will still return undefined.
 *
 * However, let (const) will not hoist!!! Only var hoist.
 *
 **/


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
console.log('x == undefined ? ' + (x == undefined));

// true
console.log('x === undefined ? ' + (x === undefined));

// true
console.log('x == null ? ' + (x == null));

// false
console.log('x === null ? ' + (x === null));

// false
console.log('x == \'null\' ? ' + (x == 'null'));

// typeof x: undefined
console.log('typeof x: ' + (typeof x));

/**
 * Variable hoisting: variable defined later after it is used.
 **/
var x = 3;


/**
 * Variable hoisting: let and const will not hoist!!!
 **/
// const x = 3;
// let x = 3;



