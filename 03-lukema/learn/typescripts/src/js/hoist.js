
/**
 * This can be at the end of this file:
 *
 *    var x;
 *
 * It does not have to be declared before using it. One can use a variable first and declare it later.
 * This is called variable hoisting.  However, variables that are hoisted will return a value of undefined.
 * So even if you declare and initialize after you use or refer to this variable, it will still return undefined.
 *
 * However, let (const) will not hoist!!! Only var hoist.
 *
 * **/
console.log('The definition of x hoists here. But the value of x does not hoist: x = ' + x);

printline('Print this test line.');

/**
 * 1. The definition of x will hoist, but its value will not hoist.
 * */
var x = 'Hello, World!';

/**
 * 2. Function definition will hoist.
 * */
function printline(x) {
  console.log(x);
};

try {
  myfunc();
} catch (err) {
  console.log('ERROR: ' + err.message);
  // console.log(err);
} finally {
  console.log('finally called.');
}

/**
 * 3. Function assignment will not hoist.
 * */
var myfunc = function() {
  console.log('Function assign.');
};

myfunc();

/**
 * not_defined is never defined. So this line will cause 'ReferenceError: not_defined is not defined'
 * */
try {
  console.log(not_defined);
} catch (err) {
  console.log('ERROR: ' + err.message);
  console.log(err);
} finally {
  console.log('finally called.');
}


/**
 * const and let will not hoist.
 *
 * */
try {
  console.log(myConst); // const will not hoist. This line will throw exception.
} catch (err) {
  console.log('const will not hoist: ' + err.message);
}

const myConst = 'My const will not hoist.';

console.log(myConst);

/**
 * const and let will not hoist.
 *
 * */
try {
  console.log(newVar); // let will not hoist. This line will throw exception.
} catch (err) {
  console.log('let will not hoist: ' + err.message);
}

const newVar = 'New var.';

console.log(newVar);



/**
 * const and let will not hoist.
 *
 * */
try {
  console.log(netDeclared); // not declared will not hoist with assignment. This line will throw exception.
} catch (err) {
  console.log('Not delcared will not hoist: ' + err.message);
}

netDeclared = 'Not declared.';

console.log(netDeclared);

