
/**
 * npm install rxjs-compat --save-dev
 */
const Rx = require('rxjs-compat');

// This function runs when subscribe() is called
function sequenceSubscriber(observer) {
  // synchronously deliver 1, 2, and 3, then complete
  observer.next(1);
  observer.next(2);
  observer.next('Text is here.');
  observer.next(3);
  observer.complete();

  // unsubscribe function doesn't need to do anything in this
  // because values are delivered synchronously
  return { unsubscribe() { } };
}

// Create a new Observable that will deliver the above sequence
const sequence = new Rx.Observable(sequenceSubscriber);

// execute the Observable and print the result of each notification
sequence.subscribe({
  next(data) { console.log(data); },
  complete() { console.log('Finished sequence'); }
});

// Logs:
// 1
// 2
// 3
// Finished sequence

/// ///////////
/*
const ESC_KEY = 27;
const nameInput = document.getElementById('name') as HTMLInputElement;

const subscription = fromEvent(nameInput, 'keydown')
    .subscribe((e: KeyboardEvent) => {
        if (e.keyCode === ESC_KEY) {
            nameInput.value = '';
        }
    });
*/
