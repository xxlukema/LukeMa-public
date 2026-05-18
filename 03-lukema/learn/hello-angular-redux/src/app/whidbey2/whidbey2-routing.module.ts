import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Whidbey2Component } from './whidbey2.component';


const routes: Routes = [
  {
    // path: 'whidbey2', // If path value is not empty. It is eager load.
    path: '', // If path value is empty. It is lazy load. Lazy loaded feature's vavigate path is configured in app-routing.module.ts
    component: Whidbey2Component
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class Whidbey2RoutingModule { }
