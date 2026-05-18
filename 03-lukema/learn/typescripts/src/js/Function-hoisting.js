

/**
 * For functions, only the function declaration gets hoisted to the top and not the function expression.
 *
 **/

/**
 * Function declaration
 **/

// This will print "Function defined"
foo(); // "bar"

/**
 * Function declaration will hoist
 **/
function foo() {
  console.log('Function declaration will hoist.');
}


/**
 * Function expression will NOT hoist.
 **/

// func_expression(); // TypeError: func_expression is not a function

/**
 * expression with anonymous function:
 *
 **/
var func_expression = function() {
  console.log('Function expression will NOT hoist.');
};

func_expression(); // OK. Function expression will NOT hoist.


// func_named();

/**
 * Expression with named function:
 * A name can be provided with a function expression and can be used
 * inside the function to refer to itself, or in a debugger to identify
 * the function in stack traces:
 *
 **/
var func_named_expression = function func_named() {
  console.log('Named function expression');
};

func_named_expression();
// func_named();

