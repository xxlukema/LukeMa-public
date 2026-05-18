
/**
 * npm install rxjs-compat --save-dev
 * npm i rxjs --save-dev
 */
const Rx = require('rxjs-compat');

/**
 * Observable.of(...items)—Returns an Observable instance that synchronously delivers the values provided as arguments.
 * Observable.from(iterable)—Converts its argument to an Observable instance. This method is commonly used to convert an array to an observable.
 */
// Create simple observable that emits three values
const myObservable = Rx.Observable.of(1, 2, 3);

/**
 * Subscribe with positional arguments
 */
myObservable.subscribe(
  x => console.log('Observer got a next value: ' + x),
  err => console.error('Observer got an error: ' + err),
  () => console.log('Observer got a complete notification')
);

// Logs:
// Observer got a next value: 1
// Observer got a next value: 2
// Observer got a next value: 3
// Observer got a complete notification
