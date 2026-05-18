import { CommonModule, DecimalPipe } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { GoogleMapsModule } from '@angular/google-maps';
import { LoadingModule } from '../loading/loading.module';
import { Whidbey2RoutingModule } from './whidbey2-routing.module';
import { Whidbey2Component } from './whidbey2.component';
import { Whidbey2Service } from './whidbey2.service';


@NgModule({
  declarations: [
    Whidbey2Component
  ],
  imports: [
    CommonModule,
    GoogleMapsModule,
    FlexLayoutModule,
    LoadingModule,
    Whidbey2RoutingModule
  ],
  providers: [
    Whidbey2Service,
    DecimalPipe
  ]
})
export class Whidbey2Module {

  /**
    * Optional. For debugging lazy routing only.
    */
  constructor() {
    console.log('Whidbey2Module constructor.');
  }
}
