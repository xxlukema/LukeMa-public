import { cacheInterceptorProvider } from '@/app/utils/interceptors/cache.interceptor';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule } from '@angular/material/dialog';
import { MatExpansionModule } from '@angular/material/expansion';
import { MAT_FORM_FIELD_DEFAULT_OPTIONS, MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AppService } from './app.service';
import { ContactModule } from './contact/contact.module';
import { DarkModeDialogComponent } from './dark-mode-dialog/dark-mode-dialog.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { NavComponent } from './nav/nav.component';
import { PageNotFoundComponent } from './page-not-found.component';
import { ShoppingCartModule } from './shopping-cart/shopping-cart.module';
import { ToggleDarkModeMatModule } from './toggle-dark-mode-mat/toggle-dark-mode-mat.module';
import { BlockUiModule } from './utils/blockui/blockui.module';
import { jwtInterceptorProvider } from './utils/interceptors/jwt.interceptor';
import { MatTooltipModule } from '@angular/material/tooltip';
import { LoadingModule } from './utils/loading/loading.module';


@NgModule({

  /**
   * import modules.
   * "import custom module" or "declare custom component" in app.modle.ts to make it avaliable for the whole application.
   */
  imports: [
    /** async pipe is in CommonModule */
    CommonModule,
    BrowserModule,
    /* StoreModule.forRoot({}), */
    HttpClientModule,
    /**
     * Animation module is required by many PrimeNG's components.
     */
    BrowserAnimationsModule,
    /**
     * Form module will be needed to use form input components like the radio button component.
     */
    FormsModule,
    ContactModule, // -- Eager loading
    ShoppingCartModule,
    ToggleDarkModeMatModule,
    FlexLayoutModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatDialogModule,
    MatExpansionModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatTooltipModule,
    ReactiveFormsModule,
    DarkModeDialogComponent,
    BlockUiModule,
    LoadingModule,
    MatMenuModule,
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
    // ToggleDarkModeComponent
  ],
  providers: [
    { provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: { appearance: 'outline' } },
    // { provide: ErrorStateMatcher, useClass: MyErrorStateMatcher }
    //
    cacheInterceptorProvider,
    // httpErrorInterceptorProvider
    // or
    // { provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true },
    //
    jwtInterceptorProvider,
    // Or
    // { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    //
    // { provide: ErrorHandler, useClass: GlobalErrorHandlerService },
    AppService
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
