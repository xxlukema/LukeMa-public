
/**
 * npm install rxjs-compat --save-dev
 * npm i rxjs --save-dev
 */
const Rx = require('rxjs-compat');

const obs$ = Rx.Observable
  .interval(500)
  .map(value => {
    if (value == 3) {
      throw new Error('too high!');
    } else if (value > 5) {
      complete();
    } else {
      return value;
    }
  })
  /*
  .catch(error => {
    // return Rx.Observable.of(500);
    // return Rx.Observable.empty();
    // return Rx.Observable.never();
    return Rx.Observable.onErrorResumeNext();
  })
  */
/*
  .catch((error, source$) => {
    return source$;
  })
  */
/*
   .retry(2)
   .catch(error => {
     return Rx.Observable.of(777);
   })
  */
  /*
  .retryWhen(error$ => {
    return error$.delay(500);
  })
  */
  .retryWhen(error$ => {
    return error$.scan((count, currentErr) => {
      if (count > 2) {
        throw currentErr;
      } else {
        return count += 1;
      }
    }, 0);
  })
  .publish().refCount()
  .finally(() => {
    console.log('Goodbye.');
  });

obs$.subscribe(
  value => {
    console.log(value);
  },
  err => {
    console.error('Oops:', err.message);
  },
  () => {
    console.log('We\'re done here!');
  });

