import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ProductaddComponent } from './productadd.component';

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
    component: ProductaddComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductaddRoutingModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('ProductaddRoutingModule constructor.');
  }
}
