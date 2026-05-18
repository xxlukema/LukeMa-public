
/**
 * That is a promise. A promise has 3 states. They are:
 *
 *     Promise is pending: You don't know if you will get that phone until next week.
 *     Promise is resolved: Your mom really buy you a brand new phone.
 *     Promise is rejected: You don't get a new phone because your mom is not happy.
 *
 * */

/* ES6 */
let isMomHappy = true;

/* Promise */
const willIGetNewPhone1 = new Promise(

  /* executor */
  function(resolve, reject) {
    if (isMomHappy) {
      var phone = {
        brand: 'Samsung',
        color: 'black'
      };
      resolve(phone); // fulfilled
    } else {
      var reason = new Error('mom is not happy');
      reject(reason); // reject
    }
  }
);

/* Promise */
const willIGetNewPhone2 = new Promise(

  /* executor */
  (resolve, reject) => {
    if (isMomHappy) {
      var phone = {
        brand: 'Samsung',
        color: 'black'
      };
      resolve(phone); // fulfilled
    } else {
      var reason = new Error('mom is not happy');
      reject(reason); // reject
    }
  }
);

/* Consume a promise */
const askMom1 = function() {
  willIGetNewPhone1
    .then(function(fulfilled) {
      /* yay, you got a new phone */
      console.log(fulfilled);
    })
    .catch(function (error) {
      /* oops, mom don't buy it */
      console.log(error.message);
      /* output: 'mom is not happy' */
    });
};

askMom1();
isMomHappy = false;
askMom1();

/* 2nd promise */
const showOff1 = function(phone) {
  return new Promise(function(resolve, reject) {
    const message = 'showOff1: I have a new phone ' + phone.color + ' ' + phone.brand + ' phone';

    /* This will produce a message: */
    resolve(message);
  });
};

/* 3rd promise */
const showOff2 = function(phone) {
  const message = 'showOff2: I have got this message from the chain: ' + phone;

  return Promise.resolve(message);
};

/* Consume */
const askMom2 = function() {
  willIGetNewPhone2
    .then(showOff1) // chain it here. showOff1 will get fulfilled data from willIGetNewPhone2.
    .then(showOff2) // chain it here. showOff2 will get fulfilled data from showOff1
    .then(function (fulfilled) { // chain it here. It will get fulfilled data from showOff2
      console.log('showOff3: ' + fulfilled);
      // output: 'Hey friend, I have a new black Samsung phone.'
    })
    .catch(function (error) {
      // oops, mom don't buy it
      console.log(error.message);
      // output: 'mom is not happy'
    });
};

askMom2();


/* Consume */
const askMom3 = function() {
  willIGetNewPhone2
    .then(showOff1) // chain it here. showOff1 will get fulfilled data from willIGetNewPhone2.
    .then((fromSowOff1) => { // chain it here. showOff2 will get fulfilled data from showOff1
      const msg = 'showOff2: My phone is ' + fromSowOff1;
      console.log(msg);
      return Promise.resolve(msg);
    })
    .then(function(fulfilled) { // chain it here. showOff2 will get fulfilled data from showOff2
      console.log('showOff3: ' + fulfilled);
      // output: 'Hey friend, I have a new black Samsung phone.'
    })
    .catch(function(error) {
      // oops, mom don't buy it
      console.log(error.message);
      // output: 'mom is not happy'
    });
};

askMom3();

