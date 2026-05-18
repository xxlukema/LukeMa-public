

/**
 * A contrived data source to use in our "observable"
 * NOTE: this will clearly never error
 *
 * */
class DataSource {
  constructor() {
    let i = 0;
    this._id = setInterval(() => this.emit(i++), 200);
  }

  emit(n) {
    const limit = 10;

    this.ondata(n);

    if (n === limit) {
      this.oncomplete();
      this.destroy();
    }
  }

  destroy() {
    clearInterval(this._id);
  }
}


/**
 * our observable
 *
 * */
function MyObservable(observer) {
  const datasource = new DataSource();
  datasource.ondata = (e) => observer.next(e);
  datasource.onerror = (err) => observer.error(err);
  datasource.oncomplete = () => observer.complete();
  return () => {
    datasource.destroy();
  };
}


/**
 * now let's use it
 *
 * */
const unsub = MyObservable({
  next(x) { console.log('next() called. ' + x); },
  error(err) { console.error('error() called. ' + err); },
  complete() { console.log('complete() called.'); }
});

/**
 * uncomment to try out unsubscription
 *
 * */
setTimeout(unsub, 500);


