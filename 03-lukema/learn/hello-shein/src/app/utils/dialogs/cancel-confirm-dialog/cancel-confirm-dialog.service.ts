import { Injectable } from '@angular/core';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { map, take } from 'rxjs/operators';
import { CancelConfirmDialogData } from './cancel-confirm-dialog-data';
import { CancelConfirmDialogComponent } from './cancel-confirm-dialog.component';

@Injectable()
export class CancelConfirmDialogService {
  constructor(private dialog: MatDialog) { }
  matDialogRef!: MatDialogRef<CancelConfirmDialogComponent>;

  open(cancelConfirmDialogData: CancelConfirmDialogData, heightInEm: number = 12.5, widthInEm: number = 30) {
    const matDialogConfig = new MatDialogConfig();
    matDialogConfig.panelClass = 'myapp-dialog';
    matDialogConfig.data = cancelConfirmDialogData;
    matDialogConfig.disableClose = false;
    matDialogConfig.hasBackdrop = true;
    matDialogConfig.height = heightInEm + 'em';
    matDialogConfig.width = widthInEm + 'em';
    this.matDialogRef = this.dialog.open<CancelConfirmDialogComponent, CancelConfirmDialogData, any>(CancelConfirmDialogComponent, matDialogConfig);
  }

  confirmed(): Observable<any> {
    return this.matDialogRef.afterClosed().pipe(
      take(1),
      map(res => {
        return res;
      })
    );
  }
}
