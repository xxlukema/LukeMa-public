
/**
 * http://www.gistia.com/mastering-promises/
 */
const firstPromise = Promise.resolve(10);
const secondPromise = Promise.resolve(5);
const thirdPromise = Promise.resolve(20);

Promise
  .all([firstPromise, secondPromise, thirdPromise])
  .then(values => { console.log(values.reverse()); });

