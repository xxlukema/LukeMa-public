
// quick and dirty will be true for '', null, undefined, 0, NaN and false.
if (!x);

// test for null OR undefined
if (x == null);

// test for undefined OR null
if (x == undefined);

// test for undefined
if (x === undefined);

// or safer test for undefined since the variable undefined can
// be set causing tests against it to fail.
if (typeof x === 'undefined');

// test for empty string
if (x === '');

// if you know its an array
if (x.length == 0);

// or
if (!x.length);

// BONUS test for empty object
var empty = true; var fld; ;

for (fld in x) {
  empty = false;
  break;
}

// //////////////////////////////////

function empty(val) {

  // test results
  // ---------------
  // [] true, empty array
  // {} true, empty object
  // null true
  // undefined true
  // "" true, empty string
  // '' true, empty string
  // 0 false, number
  // true false, boolean
  // false false, boolean
  // Date false
  // function false

  if (val === undefined) { return true; }

  if (typeof (val) === 'function' || typeof (val) === 'number' ||
			typeof (val) === 'boolean' ||
			Object.prototype.toString.call(val) === '[object Date]') { return false; }

  if (val == null || val.length === 0) // null or 0 length array
  { return true; }

  if (typeof (val) === 'object') {
    // empty object

    var r = true;

    for (var f in val) {
      r = false;
    }

    return r;
  }

  return false;
}


/**
 * Checks if value is empty. Deep-checks arrays and objects
 * Note: isEmpty([]) == true, isEmpty({}) == true, isEmpty([{0:false},"",0]) == true, isEmpty({0:1}) == false
 * @param value
 * @returns {boolean}
 */
function isEmpty(value) {
  var isEmptyObject = function(a) {
    if (typeof a.length === 'undefined') { // it's an Object, not an Array
      var hasNonempty = Object.keys(a).some(function nonEmpty(element) {
        return !isEmpty(a[element]);
      });
      return hasNonempty ? false : isEmptyObject(Object.keys(a));
    }

    return !a.some(function nonEmpty(element) { // check if array is really not empty as JS thinks
      return !isEmpty(element); // at least one element should be non-empty
    });
  };
  return (
    value == false ||
    typeof value === 'undefined' ||
    value == null ||
    (typeof value === 'object' && isEmptyObject(value))
  );
}




