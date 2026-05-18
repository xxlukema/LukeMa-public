
/**
 * npm install rxjs-compat --save-dev
 * npm i rxjs --save-dev
 */
const Rx = require('rxjs-compat');

// This function runs when subscribe() is called
function sequenceSubscriber(observer) {

  console.log('Begin schedule.');

  let i = 0;
  setTimeout(() => { observer.next('value = ' + i++); }, 500 * 1);
  setTimeout(() => { observer.next('value = ' + i++); }, 500 * 2);
  setTimeout(() => { observer.next('value = ' + i++); }, 500 * 3);
  setTimeout(() => { observer.next('value = ' + i++); }, 500 * 4);
  setTimeout(() => { observer.next('value = ' + i++); }, 500 * 5);
  setTimeout(() => { observer.next('value = ' + i++); }, 500 * 6);
  setTimeout(() => { observer.next('value = ' + i++); }, 500 * 7);
  setTimeout(() => { observer.next('value = ' + i++); }, 500 * 8);

  console.log('end schedule.');

  // unsubscribe function doesn't need to do anything in this
  // because values are delivered synchronously
  return { unsubscribe() { } };
}

const obs$ = new Rx.Observable(sequenceSubscriber)
  .publish()
  .refCount()
  .finally(() => {
    console.log('Source says Goodbye.');
  });

const sub1 = obs$.subscribe({
  next(data) {
    if (data === 'value = 5') {
      sub1.unsubscribe();
    } else {
      console.log('Observer 1: ' + data);
    }
  },
  error(err) {
    console.error('Observer 1 Oops:', err.message);
  },
  complete() {
    console.log('Observer 1 completed. We\'re done here!');
  }
});


Rx.Observable.of('Hello World!').delay(3000)
  .toPromise()
  .then(
    data => {
      console.log('#### Promise was resolved: ' + data);
    }
  )
  .catch(
    error => {
      console.log('#### Promise was rejected: ' + error.message);
    }
  );


setTimeout(
  () => {
    const sub2 = obs$.subscribe(
      data => {
        if (data === 'value = 4') {
          sub2.unsubscribe();
        } else {
          console.log('Observer 02: ' + data);
        }
      },
      err => {
        console.error('Observer 02 Oops:', err.message);
      },
      () => {
        console.log('Observer 02 completed. We\'re done here!');
      });
  }, 1200);
