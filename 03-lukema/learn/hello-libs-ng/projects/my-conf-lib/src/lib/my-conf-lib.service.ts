import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MyConfLibService {

  constructor() {
    console.log('my-conf-lib.service', 'constructor');
  }

  sayHello(): void {
    console.log('my-conf-lib.service', 'hello');
  }
}
