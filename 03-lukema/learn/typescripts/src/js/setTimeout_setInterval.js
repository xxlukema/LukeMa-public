/**
 * 1. Both setTimeout and setInterval starts after the set timeout.
 * 2. setTimeout occurs once.
 * 3. setTimeout can be cleared prior to it execution with clearTimeout(handle).
 * 4. setInterval occurs until it is cleared.
 * 5. setInterval can be cleared with clearInterval(handle).
 * 6. Case sensitive. There are no settimeout and setinterval.
 */

console.log('Time 0');

setTimeout(() => {
  console.log('Timeout');
}, 2000);

let counter = 0;
const id = setInterval(() => {
  console.log('Interval', counter++);
}, 4000);


setTimeout(() => {
  clearInterval(id);
}, 10000);

console.log('Time 1');

