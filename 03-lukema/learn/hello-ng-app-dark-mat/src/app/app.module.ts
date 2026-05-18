import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ErrorHandler, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule } from '@angular/material/dialog';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule, MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContactModule } from './contact/contact.module';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { HomeModule } from './home/home.module';
import { NavComponent } from './nav/nav.component';
import { PageNotFoundComponent } from './page-not-found.component';
import { SampleDialogComponent } from './sample-dialog/sample-dialog.component';
import { ToggleDarkModeMatModule } from './toggle-dark-mode-mat/toggle-dark-mode-mat.module';
import { GlobalErrorHandlerService } from './utils/global-error-handler.service';
import { HttpInterceptorService } from './utils/http-Interceptor.service';


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
    HomeModule, // -- Eager loading
    ContactModule, // -- Eager loading
    ToggleDarkModeMatModule,
    FlexLayoutModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatDialogModule,
    MatExpansionModule,
    MatCheckboxModule,
    MatFormFieldModule,
    ReactiveFormsModule,
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
    SampleDialogComponent
    // ToggleDarkModeComponent
  ],
  providers: [
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
