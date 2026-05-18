import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { MyEventRoutingModule } from './my-event-routing.module';
import { MyEventComponent } from './my-event.component';


@NgModule({
  declarations: [
    MyEventComponent
  ],
  imports: [
    CommonModule,
    MyEventRoutingModule
  ]
})
export class MyEventModule { }
