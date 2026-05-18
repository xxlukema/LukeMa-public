import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { AckDialogModule } from '../components/dialogs/ack-dialog/ack-dialog.module';
import { CancelConfirmDialogModule } from '../components/dialogs/cancel-confirm-dialog/cancel-confirm-dialog.module';
import { ModalDialogModule } from '../components/dialogs/modal-dialog/modal-dialog.module';
import { ModalDialogDemoRoutingModule } from './modal-dialog-demo-routing.module';
import { ModalDialogDemoComponent } from './modal-dialog-demo.component';

@NgModule({
    declarations: [ModalDialogDemoComponent],
    imports: [
        CommonModule,
        AckDialogModule,
        CancelConfirmDialogModule,
        FlexLayoutModule,
        ReactiveFormsModule,
        MatButtonModule,
        MatIconModule,
        MatFormFieldModule,
        ModalDialogModule,
        ModalDialogDemoRoutingModule
    ]
})
export class ModalDialogDemoModule { }
