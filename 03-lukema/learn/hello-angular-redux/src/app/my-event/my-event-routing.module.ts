import { MyResolveService } from '@/app/utils/my-resolve/my-resolve.service';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyEventComponent } from './my-event.component';

const routes: Routes = [
  {
    // path: 'my-http', // If path value is not empty. It is eager load.
    path: '', // If path value is empty. It is lazy load. Lazy loaded feature's vavigate path is configured in app-routing.module.ts
    component: MyEventComponent,
    data: {
      from: 'Default'
    },
    resolve: {
      from: MyResolveService
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyEventRoutingModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('MyEventRoutingModule constructor.');
  }
}
