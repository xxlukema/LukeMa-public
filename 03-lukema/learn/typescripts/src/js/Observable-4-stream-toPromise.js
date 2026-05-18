
/**
 * npm install rxjs-compat --save-dev
 * npm i rxjs --save-dev
 */
const Rx = require('rxjs-compat');

// This function runs when subscribe() is called
function sequenceSubscriber(observer) {

  console.log('Begin schedule.');

  // synchronously deliver 1, 2, and 3, then complete
  setTimeout(() => { observer.next(1); }, 2500);
  setTimeout(() => { observer.next(2); }, 5000);
  setTimeout(() => { observer.next(3); }, 7500);
  setTimeout(() => { observer.next(4); }, 10000);
  setTimeout(() => { observer.next(5); }, 12500);
  setTimeout(() => { observer.next(6); }, 15000);
  setTimeout(() => { observer.next(7); }, 17500);
  setTimeout(() => { observer.error('Error message here.'); }, 18800);
  setTimeout(() => { observer.next(8); }, 20000);
  setTimeout(() => { observer.next(9); }, 22500);
  setTimeout(() => { observer.next(10); }, 25000);
  setTimeout(() => { observer.complete(); }, 26500);
  setTimeout(() => { observer.next(11); }, 27500);
  setTimeout(() => { observer.next(12); }, 30000);

  console.log('end schedule.');

  // unsubscribe function doesn't need to do anything in this
  // because values are delivered synchronously
  return { unsubscribe() { } };
}

/**
 * Create a new Observable that will deliver the above sequence
 *
 * Hot vs Cold Observables
 *
 * The publish operator creates an ConnectableObservable which means it creates an Observable that shares
 * one single subscription to the underlying source. It’s the job of the connect operator to actually cause
 * the ConnectableObservable to subscribe to the underlying source (the thing that produces values).
 * refCount an operator that builds up on connect and causes the ConnectableObservable to subscribe to the
 * underlying source as soon as there is a first subscriber and to unsubscribe from it as soon as there’s no
 * subscriber anymore. It simply keeps track of how many subscriptions are made to the ConnectableObservable.
 */
const sequence = new Rx.Observable(sequenceSubscriber)
  .publish()
  .refCount()
/**
     * retry will trigger complete().
     */
  .retryWhen(err => {
    // return Rx.Observable.of(500);
    // return Rx.Observable.onErrorResumeNext();
    throw new Error('Caught error: ' + err);
  }
  )
  .finally(() => {
    console.log('Goodbye.');
  });
// .retry(2);

/**
 * Subscribe with positional arguments
 */
sequence.subscribe(
  x => console.log('Observer 1 got a next value: ' + x),
  err => console.error('Observer 1 got an error: ' + err),
  () => console.log('Observer 1 got a complete notification')
);

/**
 * Promise will wait until:
 * (1) producer completes and process the last set of data
 * or
 * (2) error occurs.
 *
 */
sequence.toPromise()
  .then(
    data => console.log('#### Promise resolved a value: ' + data),
    err => { console.error('#### Promise received Error 1: ' + err); }
  );

setTimeout(() => {
  const mySubscription = sequence.subscribe(
    x => {
      console.log('Observer 2 got a next value: ' + x);
      if (x === 5) {
        console.log('Observer 2 unsubscribe value(5).');
        mySubscription.unsubscribe();
        console.log('Observer 2 unsubscribe value(5) complete.');
      }
    },
    err => {
      console.error('Observer 2 got an error: ' + err);
      mySubscription.unsubscribe();
    },
    () => console.log('Observer 2 got a complete notification')
  );
},
12000);

// Logs:
// Observer got a next value: 1
// Observer got a next value: 2
// Observer got a next value: 3
// Observer got a complete notification
