import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyObservableComponent } from './my-observable.component';

const routes: Routes = [
  {
    // path: 'my-http', // If path value is not empty. It is eager load.
    path: '', // If path value is empty. It is lazy load. Lazy loaded feature's vavigate path is configured in app-routing.module.ts
    component: MyObservableComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyObservableRoutingModule {

  /**
   * Optional. For debugging lazy routing only.
   */
   constructor() {
    console.log('MyObservableRoutingModule constructor.');
  }
}
