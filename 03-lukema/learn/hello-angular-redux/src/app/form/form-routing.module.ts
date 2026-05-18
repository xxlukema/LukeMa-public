import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormComponent } from './form.component';

const routes: Routes = [
  {
    /**
     * If a feature is eagerly loaded, the module's constructor and router's constructor will be called on
     * initial access of the website.
     */
    path: '',  // If path value is not empty. It is eager load.
    component: FormComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FormRoutingModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('FormRoutingModule constructor.');
  }
}
