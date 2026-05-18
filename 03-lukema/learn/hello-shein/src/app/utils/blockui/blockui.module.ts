import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { MatDialogModule } from '@angular/material/dialog';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { BlockUiComponent } from './blockui.component';
import { BlockUiService } from './blockui.service';


@NgModule({
  declarations: [
    BlockUiComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MatProgressBarModule,
    MatDialogModule
  ],
  exports: [BlockUiComponent],
  providers: [BlockUiService],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlockUiModule { }
