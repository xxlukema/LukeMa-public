import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AckDialogData } from '../components/dialogs/ack-dialog/ack-dialog-data';
import { AckDialogService } from '../components/dialogs/ack-dialog/ack-dialog.service';
import { CancelConfirmDialogData } from '../components/dialogs/cancel-confirm-dialog/cancel-confirm-dialog-data';
import { CancelConfirmDialogService } from '../components/dialogs/cancel-confirm-dialog/cancel-confirm-dialog.service';
import { ModalDialogData } from '../components/dialogs/modal-dialog/modal-dialog-data';
import { ModalDialogService } from '../components/dialogs/modal-dialog/modal-dialog.service';

@Component({
  standalone: false,
  selector: 'app-modal-dialog-demo',
  templateUrl: './modal-dialog-demo.component.html',
  styleUrls: ['./modal-dialog-demo.component.scss'],
})
export class ModalDialogDemoComponent implements OnInit, OnDestroy {
  constructor(
    private readonly cancelConfirmDialogService: CancelConfirmDialogService,
    private readonly ackDialogService: AckDialogService,
    private readonly modalDialogService: ModalDialogService
  ) {}

  timeout$: any;
  modal$?: Subscription;
  ack$?: Subscription;
  cancelConfirm$?: Subscription;
  modalDialog$?: Subscription;

  loading = false;

  ngOnInit(): void {
    this.loading = true;

    setTimeout(() => {
      this.loading = false;
    }, 3000);
  }

  openModalDialog() {
    const options: ModalDialogData = {
      totalTimeInseconds: 5,
    };

    this.modalDialogService.open(options);

    this.timeout$ = setTimeout(() => {
      this.modalDialogService.close();
    }, 7_000);
  }

  openAckDialog() {
    const options: AckDialogData = {
      title: 'Information',
      content: 'Please delete duplicated attachement file(s).',
      ackButtonLabel: 'YES',
    };

    this.ackDialogService.open(options);

    this.ack$ = this.ackDialogService.acked().subscribe((res) => {
      console.log('modal-dialog-demo', 'Received from dialog', res);
    });
  }

  openCancelConfirmDialog() {
    const options: CancelConfirmDialogData = {
      title: 'CANCEL REQUEST',
      content: 'Are you sure you want to cancel this request?',
      cancelButtonLabel: 'NO',
      confirmButtonLabel: 'YES',
    };

    this.cancelConfirmDialogService.open(options);

    this.cancelConfirm$ = this.cancelConfirmDialogService
      .confirmed()
      .subscribe((res) => {
        console.log('modal-dialog-demo', 'Received from dialog', res);
      });
  }

  ngOnDestroy() {
    if (this.ack$) {
      this.ack$.unsubscribe();
    }
    if (this.cancelConfirm$) {
      this.cancelConfirm$.unsubscribe();
    }
    if (this.modalDialog$) {
      this.modalDialog$.unsubscribe();
    }

    clearTimeout(this.timeout$);
  }
}
