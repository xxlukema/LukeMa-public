import { flux2Reducer } from '@/app/flux2/flux2.reducer';
import { env } from '@/environments/environment';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { ErrorHandler, NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
// import { ErrorStateMatcher } from '@angular/material/core';
import { MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { StoreModule } from '@ngrx/store';
import { KeycloakService } from 'keycloak-angular';
import { provideNgxWebstorage } from 'ngx-webstorage';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContactModule } from './contact/contact.module';
import { fluxReducer } from './flux/flux.reducer';
import { FooterComponent } from './footer/footer.component';
// import { MyErrorStateMatcher } from '@/app/utils/my-error-state.matcher';
import { HeaderComponent } from './header/header.component';
import { HomeModule } from './home/home.module';
import { AppInterceptor } from './interceptors/app.interceptor';
import { CacheInterceptor } from './interceptors/cache.interceptor';
import { NavComponent } from './nav/nav.component';
// import { MyHttpModule } from './my-http/my-http.module';
// import { MyMatProgressBarModule } from './my-mat-progress-bar/my-mat-progress-bar.module';
import { PageNotFoundComponent } from './page-not-found.component';
import { ToggleDarkModeComponent } from './toggle-dark-mode/toggle-dark-mode.component';
import { GlobalErrorHandlerService } from './utils/global-error-handler.service';
import { HttpInterceptorService } from './utils/http-Interceptor.service';

const initializeKeycloak = (
  keycloakService: KeycloakService
): (() => Promise<boolean>) => {
  return () =>
    keycloakService.init({
      config: {
        url: env.authUrl,
        realm: env.realm,
        clientId: env.clientId
      },
      initOptions: {
        checkLoginIframe: true,
        checkLoginIframeInterval: 25,
      },
      loadUserProfileAtStartUp: true,
    });
};

@NgModule({

  /**
   * import modules.
   * "import custom module" or "declare custom component" in app.modle.ts to make it avaliable for the whole application.
   */
  imports: [
    /** async pipe is in CommonModule */
    CommonModule,
    BrowserModule,

    /**
     * TODO:
     * 1. Tested for one reducer (flux):
     *    StoreModule.forRoot({ fluxState: FluxReducer }),
     *
     * 2. Tested for one reducer (flux2):
     *    StoreModule.forRoot({ flux2State: Flux2Reducer }),
     *
     * 3. Tested for multiple reducers (both flux and flux2):
     *
     *    const rootReducer = {
     *        fluxState: FluxReducer,
     *        flux2State: Flux2Reducer
     *    };
     *    StoreModule.forRoot(rootReducer),
     */
    StoreModule.forRoot({
      fluxState: fluxReducer,
      flux2State: flux2Reducer
    }),


    /* StoreModule.forRoot({}), */
    /**
     * Animation module is required by many PrimeNG's components.
     */
    BrowserAnimationsModule,
    /**
     * Form module will be needed to use form input components like the radio button component.
     */
    FormsModule,
    HomeModule, // -- Eager loading
    ContactModule, // -- Eager loading
    // MyHttpModule, // -- Lazy loading
    // MyMatProgressBarModule, // -- Lazy loading
    /**
     *  AppRoutingModule must be the last Module in the array
     */
    AppRoutingModule
  ],
  /**
   * 1. "import custom module" or "declare custom component" in app.modle.ts to make it avaliable for the whole application.
   * 2. Declare components you wrote.
   *     ==> If a component, directive, or pipe belongs to a module in the imports array, ​donot​ re-declare it in the declarations array.
   *     ==> If you wrote it and it should belong to this module, ​do​ declare it in the declarations array.
   */
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    HeaderComponent,
    FooterComponent,
    NavComponent,
    ToggleDarkModeComponent
  ],
  providers: [
    provideNgxWebstorage(),
    provideHttpClient(withInterceptorsFromDi()),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AppInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CacheInterceptor,
      multi: true
    },
    { provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: { appearance: 'outline' } },
    // { provide: ErrorStateMatcher, useClass: MyErrorStateMatcher }
    { provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorService, multi: true },
    { provide: ErrorHandler, useClass: GlobalErrorHandlerService }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor(router: Router) {
    console.log('AppModule constructor.');
    // console.log('Routes: ', JSON.stringify(router.config, undefined, 2));
    console.log('Routes: ', JSON.stringify(router.config));
  }
}
