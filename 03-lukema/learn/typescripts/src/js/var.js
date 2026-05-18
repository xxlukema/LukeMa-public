
/**
 * It is recommended to always declare variables, regardless of whether they are in a function or global scope.
 * And in ECMAScript 5 strict mode, assigning to an undeclared variable throws an error.
 *
 * Names can contain letters, digits, underscores, and dollar signs.
 * Names must begin with a letter or $ or _
 * Names are case sensitive (y and Y are different variables)
 *
 * */

var x = 'Hello World.';

function sayHello() {

  /**
    * Redefined x hoists to the top of this functioni. x is a local var now.
    *
    * */
  console.log('\nRedefined x hoists to the top of this functioni. x is a local var now.');
  console.log('The value of x should be \'Hello World.\', but it is undefined: ' + x);

  var x = 'Hello again';
  console.log(x);
}

sayHello();

console.log(x);


function sayHi() {
  console.log('Global value of x: ' + x);

  /**
    * This will change the global value of x.
    *
    * */
  x = 'Global value has been changed.';
  console.log('New global value of x: ' + x);
}

sayHi();

console.log(x);

try {
  console.log('In ECMAScript 5 strict mode, assigning to an undeclared variable throws an error.');
  notDefined = 1;
  console.log(notDefined);
} catch (err) {
  console.log(err.message);
}


$form = 'var can starts with $ sign.';

console.log($form);


