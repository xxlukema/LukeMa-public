import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';



/**
 * Dialog
 */
export interface SubmittedDialogData {
  message: string,
}

@Component({
  selector: 'app-submitted-dialog',
  templateUrl: './submitted-dialog.component.html',
  styleUrl: './submitted-dialog.component.scss',
  standalone: true,
  imports: [
    MatButtonModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    FlexLayoutModule
  ],
})
export class SubmittedDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<SubmittedDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: SubmittedDialogData,
  ) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
