import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MyFlexRoutingModule } from './my-flex-routing.module';
import { MyFlexComponent } from './my-flex.component';
import { FlexLayoutModule } from '@angular/flex-layout';

@NgModule({
    declarations: [MyFlexComponent],
    imports: [
        CommonModule,
        MatCardModule,
        FlexLayoutModule,
        MyFlexRoutingModule
    ]
})
export class MyFlexModule { }
