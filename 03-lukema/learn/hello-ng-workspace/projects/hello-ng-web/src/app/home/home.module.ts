import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
// import { HelloNgLibModule } from 'projects/hello-ng-lib/src/lib/hello-ng-lib.module';
import { HelloNgLibModule } from 'projects/hello-ng-lib/src/public-api';
import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';


@NgModule({
  declarations: [
    HomeComponent,
  ],
  imports: [
    CommonModule,
    HelloNgLibModule,
    HomeRoutingModule
  ]
})
export class HomeModule { }
