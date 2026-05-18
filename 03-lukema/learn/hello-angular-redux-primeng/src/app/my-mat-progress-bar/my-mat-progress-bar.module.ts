import { NgModule } from '@angular/core';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MyMatProgressBarRoutingModule } from './my-mat-progress-bar-routing.module';
import { MyMatProgressBarComponent } from './my-mat-progress-bar.component';


@NgModule({
  declarations: [MyMatProgressBarComponent],
  imports: [
    MatProgressBarModule,
    MyMatProgressBarRoutingModule
  ]
})
export class MyMatProgressBarModule {
  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('MyMatProgressBarModule constructor.');
  }
}
