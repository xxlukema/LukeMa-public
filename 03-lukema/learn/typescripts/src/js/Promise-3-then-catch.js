
/**
 * http://www.gistia.com/mastering-promises/
 */
const promise1 = Promise.resolve(10);

const promise2 = Promise.reject(Error('You are rejected.'));

promise1.then(console.log, console.error);

promise2.then(console.log)
  .catch(err => console.error(err.message));


