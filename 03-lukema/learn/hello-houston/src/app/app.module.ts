import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { AdminModule } from './admin/admin.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BookModule } from './book/book.module';
import { ComposeMessageComponent } from './compose-message.component';
import { ContactModule } from './contact/contact.module';
import { DialogService } from './dialog.service';
import { ChangeBgColorDirective } from './directive/change-bg-color.directive';
import { HeroesModule } from './heroes/heroes.module';
import { HomeModule } from './home/home.module';
import { MyInterceptor } from './interceptor/my-interceptor.service';
import { LoginModule } from './login/login.module';
import { PageNotFoundComponent } from './page-not-found.component';
import { RentModule } from './rent/rent.module';
import { UserService } from './service/user.service';
import { SignupModule } from './signup/signup.module';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { NavComponent } from './nav/nav.component';



/**
 * https://angular.io/guide/bootstrapping
 *
 * The @NgModule decorator identifies AppModule as an NgModule class. @NgModule takes a metadata object that tells Angular how to compile
 * and launch the application.
 *    (a) declarations—this application's lone component.
 *    (b) imports—import BrowserModule to have browser specific services such as DOM rendering, sanitization, and location.
 *    (c) providers—the service providers.
 *    (d) bootstrap—the root component that Angular creates and inserts into the index.html host web page.
 * The default CLI application only has one component, AppComponent, so it is in both the declarations and the bootstrap arrays.
 *
 * The declarations array:
 *
 *    The module's declarations array tells Angular which components belong to that module. As you create more components, add them to
 *    declarations.
 *
 *    You must declare every component in exactly one NgModule class. If you use a component without declaring it, Angular returns
 *    an error message.
 *
 *    The declarations array only takes declarables. Declarables are components, directives and pipes. All of a module's declarables must
 *    be in the declarations array. Declarables must belong to exactly one module. The compiler emits an error if you try to declare the
 *    same class in more than one module.
 *
 *    These declared classes are visible within the module but invisible to components in a different module unless they are exported from
 *    this module and the other module imports this one.
 *
 *    A declarable can only belong to one module, so only declare it in one @NgModule. When you need it elsewhere, import the module that
 *    has the declarable you need in it.
 *
 *    The key point here is that you have to export it so you can import it elsewhere.
 *
 * The imports array:
 *
 *    The module's imports array appears exclusively in the @NgModule metadata object. It tells Angular about other NgModules that this
 *    particular module needs to function properly.
 *
 *    This list of modules are those that export components, directives, or pipes that the component templates in this module reference.
 *
 * https://angular.io/guide/ngmodule
 *
 * 0. import: You import FormsModule and the new HeroFormComponent.
 *
 * 1. imports: is used to import supporting modules likes FormsModule, RouterModule, CommonModule, or any other custom-made feature module.
 *    Importing BrowserModule made all of its public components, directives, and pipes visible to the component templates in AppModule.
 *
 *    You must import those modules before you can use their directives. You add the FormsModule to the list of imports defined in the
 *    @NgModule decorator. This gives the application access to all of the template-driven forms features, including ngModel.
 *
 * 2. declarations: is used to declare components, directives, pipes that belongs to the current module. Everything inside declarations
 *    knows each other.
 *
 *    ==> If a component, directive, or pipe belongs to a module in the imports array, ​don't​ re-declare it in the declarations array.
 *    ==> If you wrote it and it should belong to this module, ​do​ declare it in the declarations array.
 *
 *    You declare any components in your declarations. Any components used in the routing of that module, must be declared in that module.
 *    If components are used in another module, then you only list them in that other module.
 *
 * 3. providers: (service providers / @Injectable) is used to inject the services required by components, directives, pipes in our module.
 *    You've defined and used the service. Now to provide it for all components to use, add it to a providers property in the AppModule.
 *    Application-scoped providers: add ContactService to the AppModule metadata's providers list:
 *    The `ContactService` provider is _application_-scoped because Angular registers a module's `providers` with the
 *    application's *root injector*.
 *
 * 4. bootstrap:
 *
 * 5. Pipes
 *    https://angular.io/guide/pipes
 *    Angular pipes, a way to write display-value transformations that you can declare in your HTML.
 *    <p>The hero's birthday is {{ birthday | date:"MM/dd/yy" }} </p>
 *    {{ birthday | date:'fullDate' | uppercase }}
 *    Custom pipes
 *
 * 6. Resolve directive conflicts
 *
 * 7. Feature modules: A feature module is a class adorned by the @NgModule decorator and its metadata,
 *    just like a root module. Feature module metadata have the same properties as the metadata for a root module.
 *
 * 8. Lazy-loading modules with the router
 *    8.1 App routing
 *    8.2 Routing to a feature module
 *    8.3 Lazy-loaded routing to a module
 *
 *    <a routerLink="eager">Eager</a>
 *    <a routerLink="lazy">Lazy</a>
 *
 *    const routes: Routes = [
 *      { path: '', redirectTo: 'eager', pathMatch: 'full' },
 *      { path: 'eager', component: EagerComponent },
 *      { path: 'lazy', loadChildren: 'lazy/lazy.module#LazyModule' }];
 *
 * 9. Prevent reimport of the CoreModule: @Optional() @SkipSelf()
 *
 * 10. Compile just-in-time (JIT): src/main.ts (dynamic)
 *     import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
 *     platformBrowserDynamic().bootstrapModule(AppModule);
 *
 * 11. Compile ahead-of-time (AOT): src/main.ts (static)
 *     import { platformBrowser } from '@angular/platform-browser';
 *     platformBrowser().bootstrapModuleFactory(AppModuleNgFactory);
 *
 *
 */
@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    /**
     *  User Modules:
     */
    HeroesModule,
    HomeModule,
    LoginModule,
    SignupModule,
    AdminModule,
    RentModule,
    BookModule,
    ContactModule,
    /**
     *  AppRoutingModule must be the last Module in the array
     */
    AppRoutingModule,
    BrowserAnimationsModule
  ],
  /**
   * Declare components you wrote.
   *   ==> If a component, directive, or pipe belongs to a module in the imports array, ​don't​ re-declare it in the declarations array.
   *   ==> If you wrote it and it should belong to this module, ​do​ declare it in the declarations array.
   */
  declarations: [
    AppComponent,
    ComposeMessageComponent,
    ChangeBgColorDirective,
    PageNotFoundComponent,
    HeaderComponent,
    FooterComponent,
    NavComponent
  ],
  providers: [
    DialogService,
    /**
     * These donot have to be in root module. It can be provided by any child module if that module
     * is imported by root module.
     */
    UserService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: MyInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  // Diagnostic only: inspect router configuration
  constructor(router: Router) {
    console.log('Routes: ', JSON.stringify(router.config, undefined, 2));
  }
}
