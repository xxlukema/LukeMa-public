import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyFlexComponent } from './my-flex.component';

const routes: Routes = [{
    path: '',
    component: MyFlexComponent
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class MyFlexRoutingModule { }
