
/**
 * http://www.gistia.com/mastering-promises/
 */
const promise = new Promise((resolve, reject) => {

  console.log('Start.');

  setTimeout(
    () => { resolve('My Data.'); },
    10000
  );

  console.log('End.');

  // reject('My error.');
  // throw new Error('My throw.');
});

promise.then(
  data => { console.log('Received data', data); },
  /**
     * With err handler inside, outside catch will not be called.
     * Without err handler inside, outside catch will be called.
     */
  err => { console.error('Error 1: ' + err); }
).catch(err => { console.error('Error 2: ' + err); });


const promise1 = Promise.resolve(10);

const promise2 = Promise.reject(Error('You are rejected.'));

promise1.then(console.log, console.error);

promise2.then(console.log)
  .catch(err => console.error(err.message));
