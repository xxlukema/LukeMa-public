import { Component, HostListener, Inject, OnDestroy, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ModalDialogData } from './modal-dialog-data';

@Component({
  standalone: false,
    selector: 'app-cancel-confirm-dialog',
    templateUrl: './modal-dialog.component.html',
    styleUrls: ['./modal-dialog.component.scss']
})
export class ModalDialogComponent implements OnInit, OnDestroy {
    constructor(
        @Inject(MAT_DIALOG_DATA) public modalDialogData: ModalDialogData,
        private readonly mdDialogRef: MatDialogRef<ModalDialogComponent>) { }

    color = 'primary';
    mode = 'determinate';
    value = 0;
    intervalId: any;

    ngOnInit() {
        let totalTimeInMils = this.modalDialogData.totalTimeInseconds * 1_000;
        if (totalTimeInMils === 0) {
            totalTimeInMils = 10_000;
        }

        // increment 5%
        const increment = totalTimeInMils / 20;
        this.intervalId = setInterval(
            () => {
                if (this.value >= 100) {
                    clearInterval(this.intervalId);
                } else {
                    this.value += 5;
                }
            },
            increment
        );
    }

    ack() {
        this.close('ack');
    }

    close(value: any) {
        clearInterval(this.intervalId);
        this.mdDialogRef.close(value);
    }

    @HostListener('keydown.esc')
    public onEsc() {
        this.close('ESC keydown');
    }

    ngOnDestroy() {
        clearInterval(this.intervalId);
    }
}
