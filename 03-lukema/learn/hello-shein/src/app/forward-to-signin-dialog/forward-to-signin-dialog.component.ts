import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router, NavigationExtras } from '@angular/router';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { DialogData } from '../sell/find-match/dialog/confirm-details-dialog';




@Component({
  selector: 'app-forward-to-signin-dialog',
  templateUrl: './forward-to-signin-dialog.component.html',
  styleUrl: './forward-to-signin-dialog.component.scss',
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
export class ConfirmDetailsDialog {
  constructor(
    public dialogRef: MatDialogRef<ConfirmDetailsDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private router: Router
  ) { }

  toDraft() {

    const navigationExtras: NavigationExtras = {
      state: {
        title: this.data.title,
        condition: this.data.condition,
      }
    };

    console.debug('----------- state', navigationExtras);


    this.router.navigate(['/sell/draft'], navigationExtras);
  }
}
