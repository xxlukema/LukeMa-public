
/**
 * https://medium.com/javascript-in-plain-english/async-await-javascript-5038668ec6eb
 *
 * Async:
 *
 * Async functions enable us to write promise based code as if it were synchronous, but without blocking the
 * execution thread. It operates asynchronously via the event-loop. Async functions will always return a value.
 * Using async simply implies that a promise will be returned, and if a promise is not returned, JavaScript
 * automatically wraps it in a resolved promise with its value.
 *
 */

console.log('time 0.');

async function firstAsync() {

  console.log('firstAsync() is called. ');

  setTimeout(() => {
    console.log('After 3 seconds, now first is done!');
  },
  3000
  );

  return 'This returned immediately, without waiting or blocking by last setTimeout.';
}

console.log('time 1');

firstAsync().then(value => {
  console.log('Before or after time 2. Return value from firstAsync():', value);
});

console.log('time 2');

/**
 * try/catch
 */
async function with_err() {
  console.log('with_err() is called. ');
  const promise = new Promise((resolve, reject) => {
    setTimeout(() => reject('1 second after time 0. with_err rejected!'), 1000);
  });
  return promise;
}

console.log('time 3');

with_err().then(value => {
  console.log('Before or after time 4. Return value from with_err():', value);
}).catch(err => {
  console.error('Before or after time 4. ERROR from with_err():', err);
});

console.log('time 4');


async function secondAsync() {
  const promise = new Promise((resolve, reject) => {
    setTimeout(() => {
      console.log('secondAsync: 1 second after time 0.');

      // resolve("secondAsync: 1 second after time 0. Done!");
      reject('secondAsync: 1 second after time 0. REJECT!');
    },
    1000
    );
  });

  try {
    // wait until the promise returns us a value
    const result = await promise;

    // "Now it's done!"
    console.log('secondAsync: After "1 second after time 0". Return value from promise:', result);

    /**
        * 1. '.then()' MUST be followed by '.catch()'
        *    primise.then(result => {}) MUST be followed by .catch(err => {})
        *    primise.then(result => {}).catch(err => {})
        * 2. If no promise.then().catch(), a try/catch block MUST be used.
        */
    /*
       primise.then(result => {
          console.log('secondAsync: After "1 second after time 0". Return value from promise:', result);
       });
       * primise.then(result => {}).catch(err => {})
       */
  } catch (err) {
    console.error('ERROR - secondAsync: 1 second after time 0.', err);
  }
}

console.log('time 3');

secondAsync();

console.log('time 4');

/**
 * Await:
 *
 * The await operator is used to wait for a Promise. It can be used inside an Async block only. The keyword
 * 'Await' makes JavaScript wait until the promise returns a result. It has to be noted that it only makes the
 * async function block wait and not the whole program execution.
 */
async function thirdAsync() {
  const promise = new Promise((resolve, reject) => {
    setTimeout(() => reject('1 second after time0. thirdAsync() rejected!'), 1000);
  });

  // wait until the promise returns us a value
  const result = await promise.then((value) => {
    console.log('Return value from thirdAsync():', value);
  }).catch(err => {
    console.log('ERROR from thirdAsync():', err);
  });

  // "Now it's done!"
  console.log('Return value from thirdAsync():', result);
}

console.log('time 5');

thirdAsync();

/**
 * We can’t use the await keyword inside of regular functions.
 *
 *    function async1() {
 *       let promise = Promise.resolve(10);
 *       let result = await promise; // SyntaxError: await is only valid in async function
 *    }
 *
 *    async1();
 */

/**
 * Async Await makes execution sequential
 */
async function sequence() {
  await promise1(50); // Wait 50ms…
  await promise2(50); // …then wait another 50ms.
  return 'done!';
}

/**
 * Async Await makes execution sequential
 *
 * Not necessarily a bad thing, but having paralleled execution is much much faster.
 */
async function sequenceAsync() {
  const promise1 = new Promise((res, rej) => {
    setTimeout(() => res('1. sequenceAsync 1st is done!'), 1000);
  });

  const promise2 = new Promise((res, rej) => {
    setTimeout(() => res('2. sequenceAsync 2nd is done!'), 1000);
  });

  // wait until the promise returns us a value
  const result1 = await promise1;

  // "Now it's done!"
  console.log('1. sequenceAsync Return value from 1st:', result1);

  // wait until the promise returns us a value
  const result2 = await promise2;

  // "Now it's done!"
  console.log('2. sequenceAsync Return value from 2nd:', result2);
}

console.log('time 6');

sequenceAsync();


/**
 * Use Promise.all() to make all passed promises sequnsial
 *
 * The Promise.all() method returns a single Promise that resolves when all of the promises passed as an iterable
 * have resolved or when the iterable contains no promises. It rejects with the reason of the first promise that rejects.
 */
async function parallelAsync() {

  const promise1 = new Promise((res, rej) => {
    setTimeout(() => res('3. parallelAsync 3rd is done!'), 1000);
  });

  const promise2 = new Promise((res, rej) => {
    setTimeout(() => res('4. parallelAsync 4th is done!'), 1000);
  });

  const result2 = await Promise.all([promise1, promise2]);

  // "Now it's done!"
  console.log('parallelAsync Return value from all:', result2);
}

parallelAsync();

