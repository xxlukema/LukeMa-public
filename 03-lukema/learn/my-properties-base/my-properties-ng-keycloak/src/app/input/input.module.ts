import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { InputRoutingModule } from './input-routing.module';
import { InputComponent } from './input.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
    declarations: [InputComponent],
    imports: [
        CommonModule,
        InputRoutingModule,
        ReactiveFormsModule,
        MatInputModule,
        MatFormFieldModule
    ]
})
export class InputModule { }
