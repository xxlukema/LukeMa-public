import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { ProgressComponent } from './progress.component';


@NgModule({
  declarations: [
    ProgressComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MatProgressBarModule
  ],
  exports: [ProgressComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProgressModule { }
