
/**
 * x is null. but it is an object.
 * null is an object.
 *
 * */
var x = null;

console.log('typeof x == ' + typeof x);

if (typeof x == null) {
  console.log('typeof x == null');
}

if (typeof x === 'null') {
  console.log('typeof x == \'null\'');
}

if (typeof x === undefined) {
  console.log('typeof x == undefined');
}

if (typeof x === 'undefined') {
  console.log('typeof x == \'undefined\'');
}

console.log('x == null ? ' + (x == null));
console.log('x == undefined ? ' + (x == undefined));

/**
 * z is never defined.
 *
 * */
console.log('typeof z == ' + typeof z);

if (typeof z == null) {
  console.log('typeof z == null');
}

if (typeof z === 'null') {
  console.log('typeof z == \'null\'');
}

if (typeof z === undefined) {
  console.log('typeof z == undefined');
}

if (typeof z === 'undefined') {
  console.log('typeof z == \'undefined\'');
}


// console.log("z == null ? " + z == null);
// console.log("z == undefined ? " + z == undefined);
//


console.log('isEmpty(x) ? ' + isEmpty(x));
// console.log("isEmpty(z) ? " + isEmpty(z));
console.log('isEmpty(0) ? ' + isEmpty(0));
console.log('isEmpty(\'\') ? ' + isEmpty(''));
console.log('isEmpty(\' \') ? ' + isEmpty(' '));

console.log('\'null\' == null ? ' + ('null' == null));
console.log('\'undefined\' == undefined ? ' + (undefined == 'undefined'));
console.log('undefined == null ? ' + (undefined == null));
console.log('null == undefined ? ' + (undefined == null));

function isEmpty(x) {
  if (typeof x === 'undefined' || x == null || x == undefined || x.length == 0) {
    return true;
  } else {
    return false;
  }
}

if (x) {
  console.log('if(x) is true when x == null');
} else {
  console.log('if(x) is false when x == null');
}

x = 0;

if (x) {
  console.log('if(x) is true when x == 0');
} else {
  console.log('if(x) is false when x == 0');
}

x = {};

if (x) {
  console.log('if(x) is true when x == {}');
} else {
  console.log('if(x) is false when x == {}');
}

