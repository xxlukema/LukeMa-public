
/**
 * If a variable is never defined/declared, Exception ReferenceError will occur.
 *
 * This is a wrong way to access an undefined variable:
 *
 * */
try {
  if (never_defined) {
    console.log('This is OK: if(never_defined) ');
  } else {
    console.log('never_defined is never defined');
  }
} catch (err) {
  console.log('1111 Not allowed: if(never_defined)');
  // console.log(err);
}


/**
 * This is a wrong way to access an undefined variable:
 *
 * */
try {
  if (never_defined == null) {
    console.log('This is OK: if(never_defined == null) ');
  } else {
    console.log('never_defined is never defined');
  }
} catch (err) {
  console.log('2222 Not allowed: if(never_defined == null) ');
  // console.log(err);
}


try {
  if (never_defined == undefined) {
    console.log('This is OK: if(never_defined == undefined) ');
  } else {
    console.log('never_defined is never defined');
  }
} catch (err) {
  console.log('3333 Not allowed: if(never_defined == undefined) ');
  // console.log(err);
}


/**
 * The only safe way to access an undefined variable:
 *
 * */
try {
  if (typeof never_defined === 'undefined') {
    console.log('This is OK: if(typeof never_defined == \'undefined\')');
  } else {
    console.log('never_defined is never defined');
  }
} catch (err) {
  console.log('4444 ' + err.message);
  console.log(err);
}



