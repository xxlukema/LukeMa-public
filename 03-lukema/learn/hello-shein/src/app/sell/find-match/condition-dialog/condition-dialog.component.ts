import { Component, HostListener, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';



/**
 * Dialog
 */
export interface ConditionDialogData {
  condition: string;
  conditions: string[];
}

@Component({
  selector: 'app-condition-dialog',
  templateUrl: './condition-dialog.component.html',
  styleUrl: './condition-dialog.component.scss',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatRadioModule,
    FlexLayoutModule
  ],
})
export class ConditionDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<ConditionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ConditionDialogData
  ) {
    console.debug('----- ConditionDialog data', this.data);
  }

  conditions: string[] = this.data.conditions;

  onNoClick(): void {
    this.close('noClick');
  }

  close(value: string) {
    this.dialogRef.close(value);
  }

  confirm() {
    this.close(this.data.condition);
  }

  @HostListener('keydown.esc')
  public onEsc() {
    this.close('ESC keydown');
  }

}
