import { Injectable } from '@angular/core';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { ModalDialogData } from './modal-dialog-data';
import { ModalDialogComponent } from './modal-dialog.component';

@Injectable()
export class ModalDialogService {
  constructor(private readonly matDialog: MatDialog) { }
  matDialogRef!: MatDialogRef<ModalDialogComponent>;

  open(ackDialogData: ModalDialogData) {
    const matDialogConfig = new MatDialogConfig();
    matDialogConfig.panelClass = 'myapp-modal';
    matDialogConfig.data = ackDialogData;
    matDialogConfig.disableClose = true;
    matDialogConfig.hasBackdrop = true;
    // matDialogConfig.height = '350px';
    // matDialogConfig.width = '600px';
    this.matDialogRef = this.matDialog.open<ModalDialogComponent, ModalDialogData, any>(ModalDialogComponent, matDialogConfig);
  }

  close() {
    this.matDialogRef.close('true');
  }
}
