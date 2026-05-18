import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { MatCardModule } from '@angular/material/card';
import { CenterRoutingModule } from './center-routing.module';
import { CenterComponent } from './center.component';

@NgModule({
    declarations: [CenterComponent],
    imports: [
        CommonModule,
        MatCardModule,
        FlexLayoutModule,
        CenterRoutingModule
    ]
})
export class CenterModule { }
