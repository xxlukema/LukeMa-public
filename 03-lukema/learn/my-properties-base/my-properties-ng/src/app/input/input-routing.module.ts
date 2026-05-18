import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InputComponent } from './input.component';

const routes: Routes = [
  {
    path: '',
    component: InputComponent,
    // canActivate: [PropGuard],
    // canActivateChild: [PropGuard],
    // runGuardsAndResolvers: 'always'
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class InputRoutingModule {}
