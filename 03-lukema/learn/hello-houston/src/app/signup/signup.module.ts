import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { SigninComponent } from './signup.component';
import { SigninRoutingModule } from './signup-routing.module';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    SigninRoutingModule
  ],
  declarations: [
    SigninComponent
  ],
  providers: []
})
export class SignupModule { }
