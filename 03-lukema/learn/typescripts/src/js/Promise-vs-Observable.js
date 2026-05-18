

/**
 * Promises vs Observables
 *
 * promise:
 *    -> Definition : Helps you run functions asynchronously, and use their return values (or exceptions) but only once when executed.`
 *    -> returns a single value
 *    -> not cancellable
 *    -> Not Lazy
 *
 * observable
 *    -> Definition : Helps you run functions asynchronously, and use their return values in a continous sequence(multiple times) when executed.
 *    -> works with multiple values over time
 *    -> cancellable
 *    -> supports map, filter, reduce and similar operators
 *    -> proposed feature for ES 2016
 *    -> use Reactive Extensions (RxJS)
 *    -> an array whose items arrive asynchronously over time
 *    -> By default, it is Lazy as it emits values when time progresses.
 *
 * observable.map(response => response.json())
 *           .toPromise();
 *
 * Promise:
 *
 *    -> Provide a single future value;
 *    -> Not lazy;
 *    -> Not cancellable;
 *
 * Observable:
 *
 *    -> Emits multiple values over time;
 *    -> Lazy;
 *    -> Cancellable;
 *    -> Supports map, filter, reduce and similar operators
 *
 * Observable:
 *
 *    -> Observable is just a function that takes an observer and returns a function Observer: an object with next, error.
 *    -> Observer allows to subscribe/unsubscribe to its data stream, emit next value to the observer, notify the observer
 *       about errors and inform the observer about the stream completion
 *    -> Observer provides a function to handle next value,errors and end of stream(ui events,http responses,data with web sockets).
 *    -> Works with multiple values over time
 *    -> It is cancel-able/retry-able and supports operators such as map,filter,reduce etc.
 *    -> Creating an Observable can be
 *          - Observable.create() - returns Observable that can invoke methods on -Observer
 *          - Observable.from() - converts an array or iterable into -Observable
 *          - Observable.fromEvent() - converts an event into Observable
 *          - Observable.fromPromise() - converts a Promise into Observable
 *          - Observable.range() - returns a sequence of integers in the specified range
 * Promise:
 *
 *    -> A promise represents a task that will finish in the future;
 *    -> Promises become resolved by a value;
 *    -> Promises get rejected by exceptions;
 *    -> Not cancellable and it returns a single value
 *    -> A promise expose a function (then)
 *          - then returns a new promise;
 *          - allows for the attachment of that will be executed based on state;
 *          - handlers are guaranteed to execute in order attached;
 *
 * */




