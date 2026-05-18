import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FieldsetRoutingModule } from './fieldset-routing.module';
import { FieldsetComponent } from './fieldset.component';


@NgModule({
  declarations: [
    FieldsetComponent
  ],
  imports: [
    CommonModule,
    FieldsetRoutingModule
  ]
})
export class FieldsetModule { }
