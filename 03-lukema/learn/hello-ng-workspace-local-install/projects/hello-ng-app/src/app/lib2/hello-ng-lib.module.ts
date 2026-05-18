import { NgModule } from '@angular/core';
import { HelloNgLibComponent } from './hello-ng-lib.component';
import { HelloNgLibService } from './hello-ng-lib.service';



@NgModule({
  declarations: [
    HelloNgLibComponent
  ],
  imports: [
  ],
  exports: [
    HelloNgLibComponent
  ],
  providers: [
    HelloNgLibService
  ]
})
export class HelloNgLib2Module { }
