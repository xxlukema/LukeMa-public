import { NgModule } from '@angular/core';
import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';


@NgModule({
  imports: [
    HomeRoutingModule
  ],
  declarations: [HomeComponent]
})
export class HomeModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('HomeModule constructor.');
  }
}
