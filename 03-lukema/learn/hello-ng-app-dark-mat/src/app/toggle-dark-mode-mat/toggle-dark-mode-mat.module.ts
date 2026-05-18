import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { ToggleDarkModeMatComponent } from './toggle-dark-mode-mat.component';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { TooltipModule } from 'ng2-tooltip-directive';


@NgModule({
  declarations: [
    ToggleDarkModeMatComponent
  ],
  imports: [
    CommonModule,
    MatCardModule,
    MatSlideToggleModule,
    TooltipModule,
    FlexLayoutModule
  ],
  exports: [ToggleDarkModeMatComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ToggleDarkModeMatModule { }
