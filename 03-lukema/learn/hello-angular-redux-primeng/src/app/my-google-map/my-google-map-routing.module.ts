import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyGoogleMapComponent } from './my-google-map.component';


const routes: Routes = [
  {
    // path: 'my-http', // If path value is not empty. It is eager load.
    path: '', // If path value is empty. It is lazy load. Lazy loaded feature's vavigate path is configured in app-routing.module.ts
    component: MyGoogleMapComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyGoogleMapRoutingModule { }
