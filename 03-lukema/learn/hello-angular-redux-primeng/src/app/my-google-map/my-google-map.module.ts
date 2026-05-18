import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { GoogleMapsModule } from '@angular/google-maps';
import { MyGoogleMapRoutingModule } from './my-google-map-routing.module';
import { MyGoogleMapComponent } from './my-google-map.component';


@NgModule({
  declarations: [
    MyGoogleMapComponent
  ],
  imports: [
    CommonModule,
    GoogleMapsModule,
    FlexLayoutModule,
    MyGoogleMapRoutingModule
  ]
})
export class MyGoogleMapModule {

  /**
    * Optional. For debugging lazy routing only.
    */
  constructor() {
    console.log('MyGoogleMapModule constructor.');
  }
}
