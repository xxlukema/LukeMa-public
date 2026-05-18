/**
 * http://www.gistia.com/mastering-promises/
 */
const promise1 = new Promise((resolve, reject) => {
  setTimeout(resolve, 2000, 'promise 1 resolved');
});

const promise2 = new Promise((resolve, reject) => {
  setTimeout(reject, 3000, 'promise 2 rejected');
});

Promise
  .race([promise1, promise2])
  .then(console.log)
  .catch(console.log);
