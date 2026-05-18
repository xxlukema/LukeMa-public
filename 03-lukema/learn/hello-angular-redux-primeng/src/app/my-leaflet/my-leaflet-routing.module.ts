import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyLeafletComponent } from './my-leaflet.component';

const routes: Routes = [
  {
    // path: 'my-leaflet', // If path value is not empty. It is eager load.
    path: '', // If path value is empty. It is lazy load. Lazy loaded feature's vavigate path is configured in app-routing.module.ts
    component: MyLeafletComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyLeafletRoutingModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('MyLeafletRoutingModule constructor.');
  }
}
