import { Component, HostListener, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AckDialogData } from './ack-dialog-data';

@Component({
  standalone: false,
    selector: 'app-cancel-confirm-dialog',
    templateUrl: './ack-dialog.component.html',
    styleUrls: ['./ack-dialog.component.scss']
})
export class AckDialogComponent implements OnInit {
    constructor(
        @Inject(MAT_DIALOG_DATA) public ackDialogData: AckDialogData,
        private readonly mdDialogRef: MatDialogRef<AckDialogComponent>) { }

    ngOnInit() {
    }

    ack() {
        this.close('ack');
    }

    close(value: any) {
        this.mdDialogRef.close(value);
    }

    @HostListener('keydown.esc')
    public onEsc() {
        this.close('ESC keydown');
    }
}
