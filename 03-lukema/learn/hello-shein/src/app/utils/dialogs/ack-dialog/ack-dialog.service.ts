import { Injectable } from '@angular/core';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { map, take } from 'rxjs/operators';
import { AckDialogData } from './ack-dialog-data';
import { AckDialogComponent } from './ack-dialog.component';

@Injectable()
export class AckDialogService {
  constructor(private dialog: MatDialog) { }
  matDialogRef!: MatDialogRef<AckDialogComponent>;

  open(ackDialogData: AckDialogData) {
    const matDialogConfig = new MatDialogConfig();
    matDialogConfig.panelClass = 'myapp-dialog';
    matDialogConfig.data = ackDialogData;
    matDialogConfig.disableClose = true;
    matDialogConfig.hasBackdrop = true;
    // matDialogConfig.height = '350px';
    // matDialogConfig.width = '600px';
    this.matDialogRef = this.dialog.open<AckDialogComponent, AckDialogData, any>(AckDialogComponent, matDialogConfig);
  }

  acked(): Observable<any> {
    return this.matDialogRef.afterClosed().pipe(
      take(1),
      map(res => {
        return res;
      })
    );
  }
}
