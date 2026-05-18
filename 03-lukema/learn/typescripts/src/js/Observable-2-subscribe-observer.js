
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
 * (Step 1) Create observer object.
 * (Step 2) Subscribe using observer.
 */
const myObserver = {
  next: x => console.log('Observer got a next value: ' + x),
  error: err => console.error('Observer got an error: ' + err),
  complete: () => console.log('Observer got a complete notification')
};

// Execute with the observer object
myObservable.subscribe(myObserver);
// Logs:
// Observer got a next value: 1
// Observer got a next value: 2
// Observer got a next value: 3
// Observer got a complete notification
