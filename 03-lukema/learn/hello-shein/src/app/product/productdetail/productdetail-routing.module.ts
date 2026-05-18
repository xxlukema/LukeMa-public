import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ProductdetailComponent } from './productdetail.component';

const routes: Routes = [
  {
    /**
     * NOT a Sample page for lazy loading (path is empty for lazy loading).
     * It can be a sample page for eager loading (path is not empty for eager loading).
     */

    /**
     * If a feature is eagerly loaded, the module's constructor and router's constructor will be called on
     * initial access of the website.
     */
    path: '',  // If path value is not empty. It is eager load.
    component: ProductdetailComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductdetailRoutingModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('ProductdetailRoutingModule constructor.');
  }
}
