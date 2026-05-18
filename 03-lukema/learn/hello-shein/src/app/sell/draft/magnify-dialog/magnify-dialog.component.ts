import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';



/**
 * Dialog
 */
export interface MagnifyDialogData {
  index: number,
  imageBase64Data: string[]
}

@Component({
  selector: 'app-magnify-dialog',
  templateUrl: './magnify-dialog.component.html',
  styleUrl: './magnify-dialog.component.scss',
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
export class MagnifyDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<MagnifyDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: MagnifyDialogData,
  ) {
    this.pos = this.data.index;
  }

  pos = 0;

  decrease() {

    if (this.pos > 0) {
      this.pos--;
    }
  }

  increase() {
    if (this.pos < this.data.imageBase64Data.length - 1) {
      this.pos++;
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
