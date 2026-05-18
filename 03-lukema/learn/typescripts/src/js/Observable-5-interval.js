
/**
 * npm install rxjs-compat --save-dev
 * npm i rxjs --save-dev
 */
const Rx = require('rxjs-compat');

const obs$ = Rx.Observable
  .interval(500)
  // .range(0, 4)
  .map(value => {
    if (value === 3) {
      return Rx.Observable.of('three');
    } else if (value > 5) {
      /**
       * complete() not defined.
       */
      // complete();
      throw new Error('too high!');
    } else {
      return value;
    }
  })
  .publish().refCount()
  .finally(() => {
    console.log('Goodbye.');
  });

obs$.subscribe(
  data => {
    console.log('Observer 1: ' + data);
  },
  err => {
    console.error('Observer 1 Oops:', err.message);
  },
  () => {
    console.log('Observer 1 completed. We\'re done here!');
  });

obs$.toPromise()
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
    obs$.subscribe(
      data => {
        console.log('Observer 02: ' + data);
      },
      err => {
        console.error('Observer 02 Oops:', err.message);
      },
      () => {
        console.log('Observer 02 completed. We\'re done here!');
      });
  }, 1000);
