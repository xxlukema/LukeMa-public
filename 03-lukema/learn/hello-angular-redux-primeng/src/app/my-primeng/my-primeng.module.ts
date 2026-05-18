import { NgModule } from '@angular/core';
import { MessageModule } from 'primeng/message';
import { MessagesModule } from 'primeng/messages';
import { ButtonModule } from 'primeng/button';
import { MyPrimengRoutingModule } from './my-primeng-routing.module';
import { MyPrimengComponent } from './my-primeng.component';
import { ToastModule } from 'primeng/toast';


@NgModule({
  imports: [
    MyPrimengRoutingModule,
    ButtonModule,
    MessagesModule,
    MessageModule,
    ToastModule
  ],
  declarations: [MyPrimengComponent]
})
export class MyPrimengModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('MyPrimengModule constructor.');
  }
}
