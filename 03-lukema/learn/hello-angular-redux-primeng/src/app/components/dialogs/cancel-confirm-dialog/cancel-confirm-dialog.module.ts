import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { CancelConfirmDialogComponent } from './cancel-confirm-dialog.component';
import { CancelConfirmDialogService } from './cancel-confirm-dialog.service';

@NgModule({
    declarations: [CancelConfirmDialogComponent],
    imports: [
        CommonModule,
        MatIconModule,
        MatButtonModule,
        FlexLayoutModule,
        MatDialogModule
    ],
    exports: [CancelConfirmDialogComponent],
    entryComponents: [CancelConfirmDialogComponent],
    providers: [CancelConfirmDialogService]
})
export class CancelConfirmDialogModule { }
