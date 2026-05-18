
try {
  if (x) {
    console.log('if(undefined) true');
  } else {
    console.log('if(undefined) false');
  }
} catch (err) {
  console.error('Exception with if.', err);
}

/**
 * hoist
 */
var x;

if (typeof y === 'undefined') {
  console.log('typeof y === undefined detected.');
} else {
  console.log('typeof y === undefined NOT detected.');
}


