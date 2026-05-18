import { flux2Reducer } from '@/app/flux2/flux2.reducer';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { StoreModule } from '@ngrx/store';
import { NgxWebstorageModule } from 'ngx-webstorage';
/**
 * One caveat however with the import from 'primeng/primeng': all PrimeNG’s components will be imported.
 * This can bloat your bundle size unnecessarily. The solution is to import each module using the full path instead.
 */
// import { AccordionModule } from 'primeng/primeng';
// import { PanelModule } from 'primeng/primeng';
// import { ButtonModule } from 'primeng/primeng';
// import { RadioButtonModule } from 'primeng/primeng';
// import { AccordionModule } from 'primeng/accordion';
// import { PanelModule } from 'primeng/panel';
// import { ButtonModule } from 'primeng/button';
// import { ButtonModule } from 'primeng/primeng';
// import { RadioButtonModule } from 'primeng/radioButton';
// import { MenuItem } from 'primeng/api';
import { MessageService } from 'primeng/api';
import { MenubarModule } from 'primeng/menubar';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContactModule } from './contact/contact.module';
import { fluxReducer } from './flux/flux.reducer';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { HomeModule } from './home/home.module';
import { AppInterceptor } from './interceptors/app.interceptor';
import { CacheInterceptor } from './interceptors/cache.interceptor';
import { NavComponent } from './nav/nav.component';
// import { MyPrimengTableModule } from './my-primeng-table/my-primeng-table.module';
// import { MyPrimengModule } from './my-primeng/my-primeng.module';
// import { MyHttpModule } from './my-http/my-http.module';
// import { MyMatProgressBarModule } from './my-mat-progress-bar/my-mat-progress-bar.module';
import { PageNotFoundComponent } from './page-not-found.component';
import { ToggleDarkModeComponent } from './toggle-dark-mode/toggle-dark-mode.component';


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
        HttpClientModule,
        /**
         * Animation module is required by many PrimeNG’s components.
         */
        BrowserAnimationsModule,
        /**
         * Form module will be needed to use form input components like the radio button component.
         */
        FormsModule,
        /**
         * PrimeNG Modules Starts
         */
        // AccordionModule,
        // PanelModule,
        // ButtonModule,
        // RadioButtonModule,
        MenubarModule,
        NgxWebstorageModule.forRoot(),
        /**
         * PrimeNG Ends
         */
        HomeModule, // -- Eager loading
        ContactModule, // -- Eager loading
        // MyHttpModule, // -- Lazy loading
        // MyPrimengModule, // -- Lazy loading
        // MyPrimengTableModule, // -- Lazy loading
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
        MessageService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AppInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: CacheInterceptor,
            multi: true
        }
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
