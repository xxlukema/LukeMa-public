"use strict";

function dupeChecker(array) {
  let newArray = {}; // = Oject.create(null);

  console.log(newArray);

  for (let i = 0; i < array.length; i++) {
    let value = array[i];
    if (value in newArray) {
      return true;
    }
    newArray[value] = true;
    console.log(newArray);
  }
  return false;
}

let a = [1, 2, 4, 3];

let b = dupeChecker(a);

console.log(b);