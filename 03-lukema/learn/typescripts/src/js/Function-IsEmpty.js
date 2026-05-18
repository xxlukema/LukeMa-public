
var x = '';

console.log(isEmpty(x));



function isEmpty(x) {

  if (typeof x === 'undefined') {
    console.log('It is never defined of a type and assigned a value.');
    return true;
  }

  if (x == undefined) {
    console.log('It is defined but never assigned a value.');
    return true;
  }

  if (x == null) {
    console.log('It is null.');
    return true;
  }

  if (x == '') {
    console.log('It is s string with 0 length.');
    return true;
  }

}

