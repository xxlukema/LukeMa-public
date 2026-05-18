import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FieldsetComponent } from './fieldset.component';

const routes: Routes = [
  {
    /**
     * If a feature is eagerly loaded, the module's constructor and router's constructor will be called on
     * initial access of the website.
     */
    path: '',  // If path value is not empty. It is eager load.
    component: FieldsetComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FieldsetRoutingModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('HomeRoutingModule constructor.');
  }
}
