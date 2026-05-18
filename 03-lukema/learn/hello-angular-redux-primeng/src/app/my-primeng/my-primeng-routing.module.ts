import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MyPrimengComponent } from './my-primeng.component';

const routes: Routes = [
  {
    // path: 'my-primeng', // If path value is not empty. It is eager load.
    path: '', // If path value is empty. It is lazy load. Lazy loaded feature's vavigate path is configured in app-routing.module.ts
    component: MyPrimengComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyPrimengRoutingModule {
  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('MyPrimengRoutingModule constructor.');
  }
}
