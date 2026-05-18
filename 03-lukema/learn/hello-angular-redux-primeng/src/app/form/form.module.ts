import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { FormRoutingModule } from './form-routing.module';
import { FormComponent } from './form.component';


@NgModule({
  declarations: [FormComponent],
  imports: [
    CommonModule,
    MatSelectModule,
    FormModule,
    MatFormFieldModule,
    FormRoutingModule
  ]
})
export class FormModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('FormModule constructor.');
  }
}
