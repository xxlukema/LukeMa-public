import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MyConfLibModule } from '@luke/my-conf-lib';
import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';


@NgModule({
  declarations: [
    HomeComponent,
  ],
  imports: [
    CommonModule,
    MyConfLibModule,
    /**
     * 1. ConfigModule.forRoot(environment, 'path config') must be imported in app.module.ts to invoke APP_INITIALIZER(s)'.
     * 2. If ConfigModule.forRoot(environment, 'path config') is imported from home.module.ts, then the APP_INITIALIZER(s) will not be invoked.
     */
    // ConfigModule.forRoot(environment, 'path config'),
    HomeRoutingModule
  ]
})
export class HomeModule { }
