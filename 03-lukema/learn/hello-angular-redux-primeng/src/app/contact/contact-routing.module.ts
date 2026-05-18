import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContactComponent } from './contact.component';


const routes: Routes = [
  {
    /**
     * If a feature is eagerly loaded, the module's constructor and router's constructor will be called on
     * initial access of the website.
     */
    path: 'contact', // If path value is not empty. It is eager load.
    // path: '',  // If path value is empty. It is lazy load. Lazy loaded feature's vavigate path is configured in app-routing.module.ts
    component: ContactComponent,
    data: { email: 'x.luke.ma@gmail.com' }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ContactRoutingModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('ContactRoutingModule constructor.');
  }
}
