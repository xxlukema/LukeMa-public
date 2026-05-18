import { NgModule } from '@angular/core';
import { MyConfLibComponent } from './my-conf-lib.component';
import { MyConfLibService } from './my-conf-lib.service';


@NgModule({
  declarations: [MyConfLibComponent],
  imports: [],
  exports: [MyConfLibComponent],
  providers: [MyConfLibService]
})
export class MyConfLibModule { }
