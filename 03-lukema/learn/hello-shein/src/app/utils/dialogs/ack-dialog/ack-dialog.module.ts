import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { AckDialogComponent } from './ack-dialog.component';
import { AckDialogService } from './ack-dialog.service';

@NgModule({
  declarations: [AckDialogComponent],
  imports: [
    CommonModule,
    MatIconModule,
    MatButtonModule,
    FlexLayoutModule,
    MatDialogModule
  ],
  exports: [AckDialogComponent],
  providers: [AckDialogService]
})
export class AckDialogModule { }
