
/**
 * npm install rxjs-compat --save-dev
 * npm i rxjs --save-dev
 */
const Rx = require('rxjs-compat');

function fromEvent(target, eventName) {
  return new Rx.Observable((observer) => {
    const handler = (e) => observer.next(e);

    // Add the event handler to the target
    target.addEventListener(eventName, handler);

    return () => {
      // Detach the event handler from the target
      target.removeEventListener(eventName, handler);
    };
  });
}

// Create a new Observable that will deliver the above sequence
const observable = fromEvent;

// execute the Observable and print the result of each notification
observable().subscribe({
  next(num) { console.log(num); },
  complete() { console.log('Finished sequence'); }
});

// Logs:
// 1
// 2
// 3
// Finished sequence

