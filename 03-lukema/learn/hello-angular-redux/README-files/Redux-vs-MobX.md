# Redux vs MobX

## Avoid Using Redux in Angular

[Why you should NEVER use Redux with Angular]<https://www.stackchief.com/blog/Why%20you%20should%20NEVER%20use%20Redux%20with%20Angular>

1. Use `RxJS` instead of `Redux` for pub/sub in Angular
2. Singleton service injection (DI) can also be used to share data across components.
3. `Redux` is for `ReactJS`
4. With `ReactJs` and `VueJs`, just like `Redux` makes it easier to share state, `Axios` makes it easier to make Ajax requests.
5. Just like Angular doesn't need `Axios` because of it's own `httpClientModule`, it doesn't need `Redux` because of things like **DI**, **services**, and **RxJS**..
6. However, in my opion (Luke Ma's opion), `Redux` enforces `Flux` Unidirectional Data Flow Pattern. With **DI** and **Services**, it is very easy to loss track of where data
   state is changed in Angular.
7. Event submission can also be used to share data across components.
