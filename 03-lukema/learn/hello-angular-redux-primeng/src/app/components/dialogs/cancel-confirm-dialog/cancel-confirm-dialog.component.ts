import { Component, HostListener, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CancelConfirmDialogData } from './cancel-confirm-dialog-data';

@Component({
    selector: 'app-cancel-confirm-dialog',
    templateUrl: './cancel-confirm-dialog.component.html',
    styleUrls: ['./cancel-confirm-dialog.component.scss']
})
export class CancelConfirmDialogComponent implements OnInit {
    constructor(
        @Inject(MAT_DIALOG_DATA) public cancelConfirmDialogData: CancelConfirmDialogData,
        private mdDialogRef: MatDialogRef<CancelConfirmDialogComponent>) { }

    ngOnInit() {
    }

    confirm() {
        this.close('confirm');
    }

    cancel() {
        this.close('cancel');
    }

    close(value: any) {
        this.mdDialogRef.close(value);
    }

    @HostListener('keydown.esc')
    public onEsc() {
        this.close('ESC keydown');
    }
}
