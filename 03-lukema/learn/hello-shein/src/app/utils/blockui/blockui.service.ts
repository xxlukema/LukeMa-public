import { Injectable } from '@angular/core';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { BlockUiComponent } from './blockui.component';
import { BlockUiData } from './blockui.data';

@Injectable()
export class BlockUiService {
  constructor(private matDialog: MatDialog) { }
  matDialogRef!: MatDialogRef<BlockUiComponent>;

  block() {
    const matDialogConfig = new MatDialogConfig();
    matDialogConfig.panelClass = 'myapp-dialog';
    matDialogConfig.disableClose = true;
    matDialogConfig.hasBackdrop = true;
    this.matDialogRef = this.matDialog.open<BlockUiComponent, BlockUiData, any>(BlockUiComponent, matDialogConfig);
  }

  lblock(timeInSeconds: number, msg: string, displayTime: boolean = true) {
    const matDialogConfig = new MatDialogConfig<BlockUiData>();
    matDialogConfig.panelClass = 'myapp-dialog';
    matDialogConfig.data = { timeInSeconds: timeInSeconds, msg: msg, displayTime: displayTime };
    matDialogConfig.disableClose = true;
    matDialogConfig.hasBackdrop = true;
    this.matDialogRef = this.matDialog.open<BlockUiComponent, BlockUiData, any>(BlockUiComponent, matDialogConfig);
  }

  unblock() {
    this.matDialogRef.close();
  }
}
