import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WindowSizeComponent } from './window-size.component';

const routes: Routes = [
  {
    path: '',
    component: WindowSizeComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WindowSizeRoutingModule { }
