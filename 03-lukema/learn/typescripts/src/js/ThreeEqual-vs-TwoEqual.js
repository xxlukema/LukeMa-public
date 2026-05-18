

/**
 * Strict equality using ===
 *
 * Strict equality compares two values for equality. Neither value is implicitly converted to
 * some other value before being compared. If the values have different types, the values are
 * considered unequal. Otherwise, if the values have the same type and are not numbers, they're
 * considered equal if they have the same value.
 * Finally, if both values are numbers, they're considered equal if they're both not NaN and
 * are the same value, or if one is +0 and one is -0.
 *
 * Loose equality using ==
 *
 * Loose equality compares two values for equality, after converting both values to a common
 * type.  After conversions (one or both sides may undergo conversions), the final equality
 * comparison is performed exactly as === performs it.
 *
 **/


var a = 1;
var b = 1;
var obj;
var c = '1';


console.log(a == b);
console.log(a === b);

console.log(a == c);
console.log(a === c); // false

console.log(obj == null);
console.log(obj === null); // false

console.log(obj == undefined);
console.log(obj === undefined);

console.log(undefined == null);
console.log(undefined === null); // false

console.log(undefined == 'undefined'); // false
console.log(undefined === 'undefined'); // false






