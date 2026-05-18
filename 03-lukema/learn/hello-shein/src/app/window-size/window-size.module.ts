import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { WindowSizeRoutingModule } from './window-size-routing.module';
import { WindowSizeComponent } from './window-size.component';


@NgModule({
    declarations: [WindowSizeComponent],
    imports: [
        CommonModule,
        FlexLayoutModule,
        WindowSizeRoutingModule
    ]
})
export class WindowSizeModule { }
