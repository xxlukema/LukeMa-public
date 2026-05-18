import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyMatTableComponent } from './my-mat-table.component';

const routes: Routes = [
  {
    // path: 'my-mat-table', // If path value is not empty. It is eager load.
    path: '', // If path value is empty. It is lazy load. Lazy loaded feature's vavigate path is configured in app-routing.module.ts
    component: MyMatTableComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyMatTableRoutingModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('MyHttpRoutingModule constructor.');
  }
}
