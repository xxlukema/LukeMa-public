import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyHttpComponent } from './my-http.component';


const routes: Routes = [
  {
    // path: 'my-http', // If path value is not empty. It is eager load.
    path: '', // If path value is empty. It is lazy load. Lazy loaded feature's vavigate path is configured in app-routing.module.ts
    component: MyHttpComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyHttpRoutingModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('MyHttpRoutingModule constructor.');
  }
}
