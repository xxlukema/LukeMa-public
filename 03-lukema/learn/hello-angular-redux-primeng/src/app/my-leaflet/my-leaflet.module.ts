import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MyLeafletRoutingModule } from './my-leaflet-routing.module';
import { MyLeafletComponent } from './my-leaflet.component';
import { MyLeafletService } from './my-leaflet.service';


@NgModule({
  declarations: [
    MyLeafletComponent
  ],
  imports: [
    CommonModule,
    MyLeafletRoutingModule
  ],
  providers: [
    MyLeafletService
  ]

})
export class MyLeafletModule { }
