import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ModalDialogDemoComponent } from './modal-dialog-demo.component';

const routes: Routes = [
    {
        path: '',
        component: ModalDialogDemoComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ModalDialogDemoRoutingModule { }
