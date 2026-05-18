
/**
 * Variables created without the keyword var, are always global, even if they are created inside a function.
 *
 **/

function without_var() {
  no_var = 'Hello. I am a global variable.';
}

// Actuallt, this doesn't work.
// console.log(no_var);


console.log('Test log.');
console.warn('Test warn.');


/**
 * A closure is a function having access to the parent scope, even after the parent function has closed.
 *
 * In the following example, The variable add is assigned the return value of a self-invoking function.
 * The self-invoking function only runs once. It sets the counter to zero (0), and returns a function expression.
 * This way add becomes a function. The "wonderful" part is that it can access the counter in the parent scope.
 * This is called a JavaScript closure. It makes it possible for a function to have "private" variables.
 * The counter is protected by the scope of the anonymous function, and can only be changed using the add function.
 *
 **/

var add = (function () {
  var counter = 0;

  console.log('This function runs only once.');

  return function () {
    counter += 1;
    console.log('counter is: ' + counter);
  };
})();

add();
add();
add();



var createPet = function(name) {
  var sex;

  return {
    setName: function(newName) {
      name = newName;
    },

    getName: function() {
      return name;
    },

    getSex: function() {
      return sex;
    },

    setSex: function(newSex) {
      if (typeof newSex === 'string' && (newSex.toLowerCase() === 'male' || newSex.toLowerCase() === 'female')) {
        sex = newSex;
      }
    }
  };
};

var pet = createPet('Vivie');
console.log(pet.getName()); // Vivie

pet.setSex('Male');
console.log(pet.getSex()); // Vivie



/**
 * Default parameters:
 * In JavaScript, parameters of functions default to undefined
 *
 **/

function multiply(a, b = 1.5) {
  return a * b;
}

console.log(multiply(5)); // 7.5


// Shorter functions:

var a = [
  'Hydrogen',
  'Helium',
  'Lithium',
  'Beryllium'
];

var a2 = a.map(function(s) { return s.length; });

console.log(a2); // logs [8, 6, 7, 9]

var a3 = a.map(s => s.length);

console.log(a3); // logs [8, 6, 7, 9]


/**
 * Array.map()
 *
 * Definition and Usage
 * The map() method creates a new array with the results of calling a function for every array element.
 *
 * The map() method calls the provided function once for each element in an array, in order.
 *
 * Note: map() does not execute the function for array elements without values.
 *
 * Note: map() does not change the original array.
 *
 **/

var numbers = [4, 9, 16, 25];

function myFunction() {
  x = document.getElementById('demo');
  x.innerHTML = numbers.map(Math.sqrt);
}

var new_numbers = numbers.map(x => Math.sqrt(x));

console.log(new_numbers);

var new_numbers = numbers.map(function (x) { return Math.sqrt(x); });

console.log(new_numbers);


/**
 * Declare the function 'myFunc'
 **/
function myFunc(theObject) {
  theObject.brand = 'Toyota';
};



/**
 * Declare variable 'mycar';
 * create and initialize a new Object;
 * assign reference to it to 'mycar'
 **/
var mycar = {
  brand: 'Honda',
  model: 'Accord',
  year: 1998
};

/**
 * Logs 'Honda'
 **/
console.log(mycar.brand);

/**
 * Pass object reference to the function
 **/
myFunc(mycar);

/**
 * Logs 'Toyota' as the value of the 'brand' property
 * of the object, as changed to by the function.
 **/
console.log(mycar.brand);


/**
 * A function can have up to 255 arguments.
 *
 **/


/**
 * One of the benefit of creating a named function expression is that in case we
 * encounted an error, the stack trace will contain the name of the function,
 * making it easier to find the origin of the error.
 *
 **/

/**
 * IIFE (Immediately Invokable Function Expression): When functions are used only once.
 *
 **/
(function() {
  console.log('Line 1');
  console.log('Line 2');
})();

(function() {
  console.log('Line 3');
}());


/**
 * Determining whether a function exists:
 * if ('function' === typeof window.noFunc) {
 *    // use noFunc()
 * } else {
 *    // do something else
 * }
 *
 **/

/**
 * Function constructor vs. function declaration vs. function expression
 *
 **/





