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

    open(cancelConfirmDialogData: CancelConfirmDialogData) {
        const matDialogConfig = new MatDialogConfig();
        matDialogConfig.panelClass = 'myapp-dialog';
        matDialogConfig.data = cancelConfirmDialogData;
        matDialogConfig.disableClose = true;
        matDialogConfig.hasBackdrop = true;
        // matDialogConfig.height = '350px';
        // matDialogConfig.width = '600px';
        this.matDialogRef = this.dialog.open(CancelConfirmDialogComponent, matDialogConfig);
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