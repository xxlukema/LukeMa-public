import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyMatProgressBarComponent } from './my-mat-progress-bar.component';

const routes: Routes = [
  {
    // path: 'my-mat-progress-bar', // If path value is not empty. It is eager load.
    path: '', // If path value is empty. It is lazy load. Lazy loaded feature's vavigate path is configured in app-routing.module.ts
    component: MyMatProgressBarComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyMatProgressBarRoutingModule {
  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('MyMatProgressBarRoutingModule constructor.');
  }
}
