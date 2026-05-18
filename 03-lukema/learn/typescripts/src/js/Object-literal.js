
/**
 * Object literals:
 * An object literal is a list of zero or more pairs of property names and
 * associated values of an object, enclosed in curly braces ({}).
 *
 **/

var sales = 'Toyota';

function carTypes(name) {
  if (name === 'Honda') {
    return name;
  } else {
    return 'Sorry, we don\'t sell ' + name + '.';
  }
}

var car = { myCar: 'Saturn', getCar: carTypes('Honda'), special: sales };

console.log(car.myCar); // Saturn
console.log(car.getCar); // Honda
console.log(car.special); // Toyota

/**
 * Update: ECMAScript 6 now allows methods to be defined the same way regardless
 * of whether they are in an object literal:
 **/
var bob = {
  age: 30,
  setAge (newAge) { /* New in ECMAScript 6 */
    this.age = newAge;
  }
};

/**
 * Additionally, you can use a numeric or string literal for the name
 * of a property or nest an object inside another. The following
 * example uses these options.
 *
 **/

var car = { manyCars: { a: 'Saab', b: 'Jeep' }, 7: 'Mazda' };

console.log(car.manyCars.b); // Jeep
console.log(car[7]); // Mazda


/**
 * Object property names can be any string, including the empty string.
 * If the property name would not be a valid JavaScript identifier or number,
 * it must be enclosed in quotes. Property names that are not valid
 * identifiers also cannot be accessed as a dot (.) property, but can be
 * accessed and set with the array-like notation("[]").
 *
 **/

var unusualPropertyNames = {
  '': 'An empty string',
  '!': 'Bang!'
};

// console.log(unusualPropertyNames.'');   // SyntaxError: Unexpected string
console.log(unusualPropertyNames['']); // An empty string
// console.log(unusualPropertyNames.!);    // SyntaxError: Unexpected token !
console.log(unusualPropertyNames['!']); // Bang!


var foo = { a: 'alpha', 2: 'two' };

console.log(foo.a); // alpha
console.log(foo[2]); // two

// console.log(foo.2);  // Error: missing ) after argument list
// console.log(foo[a]); // Error: a is not defined

console.log(foo.a); // alpha
console.log(foo['2']); // two


