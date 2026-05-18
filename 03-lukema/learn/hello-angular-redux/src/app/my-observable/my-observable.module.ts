import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyObservableRoutingModule } from './my-observable-routing.module';
import { MyObservableComponent } from './my-observable.component';


@NgModule({
  declarations: [
    MyObservableComponent
  ],
  imports: [
    CommonModule,
    MyObservableRoutingModule
  ]
})
export class MyObservableModule { }
