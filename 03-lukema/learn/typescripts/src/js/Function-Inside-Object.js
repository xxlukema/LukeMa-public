

/**
 * Function scope
 * Variables defined inside a function cannot be accessed from anywhere outside the
 * function, because the variable is defined only in the scope of the function.
 * However, a function can access all variables and functions defined inside the
 * scope in which it is defined. In other words, a function defined in the global
 * scope can access all variables defined in the global scope. A function defined
 * inside another function can also access all variables defined in its parent
 * function and any other variable to which the parent function has access.
 *
 **/

// The following variables are defined in the global scope
var num1 = 20;
var num2 = 3;
var name = 'Chamahk';

// This function is defined in the global scope
function multiply() {
  return num1 * num2;
}

multiply(); // Returns 60

// A nested function example
function getScore() {
  var num1 = 2;
  var num2 = 3;
	  var new_number = 100;

  function add() {
    return name + ' scored ' + (num1 + num2);
  }

  return add();
}

console.log(getScore()); // Returns "Chamahk scored 5"

// This is not availble:
// console.log(new_number);





