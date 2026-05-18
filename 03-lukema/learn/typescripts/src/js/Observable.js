
'use strict';

/**
 * Observables are cancellable
 * Observable are lazy
 *
 * */

const Rx = require('rxjs');
const fetch = require('node-fetch');

const Observable = Rx.Observable;
let resultA, resultB, resultC;

function addAsync(num1, num2) {
  const promise = fetch(`http://www.example.com?num1=${num1}&num2=${num2}`)
    .then(x => x.json());

  return Observable.fromPromise(promise);
}


addAsync(1, 2)
  .do(x => resultA = x)
  .flatMap(x => addAsync(x, 3))
  .do(x => resultB = x)
  .flatMap(x => addAsync(x, 4))
  .do(x => resultC = x)
  .subscribe(x => {
    console.log('total: ' + x);
    console.log(resultA, resultB, resultC);
  });




