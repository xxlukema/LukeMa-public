import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PromiseRoutingModule } from './promise-routing.module';
import { PromiseComponent } from './promise.component';

@NgModule({
  declarations: [PromiseComponent],
  imports: [
    CommonModule,
    PromiseRoutingModule
  ]
})
export class PromiseModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('PromiseModule constructor.');
  }
}
