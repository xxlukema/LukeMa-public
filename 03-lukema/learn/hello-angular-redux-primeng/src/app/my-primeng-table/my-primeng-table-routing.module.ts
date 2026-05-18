import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyPrimengTableComponent } from './my-primeng-table.component';

const routes: Routes = [
  {
    // path: 'my-primeng-table', // If path value is not empty. It is eager load.
    path: '', // If path value is empty. It is lazy load. Lazy loaded feature's vavigate path is configured in app-routing.module.ts
    component: MyPrimengTableComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyPrimengTableRoutingModule {
  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('MyPrimengTableRoutingModule constructor.');
  }
}
