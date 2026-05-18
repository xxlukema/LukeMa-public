import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ContactComponent } from './contact.component';

import { ContactRoutingModule } from './contact-routing.module';
import { ContactParentComponent } from './contact-parent.component';
import { ContactChild1Component } from './contact-child1.component';
import { ContactChild2Component } from './contact-child2.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ContactRoutingModule
  ],
  declarations: [
    ContactComponent,
    ContactParentComponent,
    ContactChild1Component,
    ContactChild2Component
  ]
})
export class ContactModule { }
