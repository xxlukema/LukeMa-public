import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { HelloNgLib2Module } from '../lib2/hello-ng-lib.module';
import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';


@NgModule({
  declarations: [
    HomeComponent,
  ],
  imports: [
    CommonModule,
    HelloNgLib2Module,
    HomeRoutingModule
  ]
})
export class HomeModule { }
