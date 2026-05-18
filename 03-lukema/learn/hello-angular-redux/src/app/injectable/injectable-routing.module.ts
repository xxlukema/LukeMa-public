import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InjectableComponent } from './injectable.component';

const routes: Routes = [{
    path: '',
    component: InjectableComponent
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class InjectableRoutingModule { }
