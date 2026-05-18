import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgxMaskModule } from 'ngx-mask';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
// import { ConfigModule } from '@luke/my-conf-lib';
// import { MyConfLibModule } from '@luke/my-conf-lib';
import { environment } from '../environments/environment';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    NgxMaskModule,
    NgxWebstorageModule.forRoot(),

    // MyConfLibModule,
    /**
     * 1. ConfigModule.forRoot(environment, 'path config') must be imported in app.module.ts to invoke APP_INITIALIZER(s)'.
     * 2. If ConfigModule.forRoot(environment, 'path config') is imported from home.module.ts, then the APP_INITIALIZER(s) will not be invoked.
     */
    // ConfigModule.forRoot(environment, 'my config value from app.module.ts'),

    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
