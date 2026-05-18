import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

import {RentComponent} from './rent.component';
import {AddRentPropertyComponent} from './add-rent-property.component';
import {RentPropertyService} from './rent-property.service';

import {RentRoutingModule} from './rent-routing.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    RentRoutingModule
  ],
  declarations: [
    RentComponent,
    AddRentPropertyComponent
  ],
  providers: [RentPropertyService]
})
export class RentModule {}
