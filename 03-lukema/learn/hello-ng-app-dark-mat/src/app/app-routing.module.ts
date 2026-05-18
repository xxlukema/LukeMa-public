import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
// import { MyHttpModule } from './my-http/my-http.module';
// import { MyMatProgressBarModule } from './my-mat-progress-bar/my-mat-progress-bar.module';
import { PageNotFoundComponent } from './page-not-found.component';
import { SelectivePreloadingStrategy } from './selective-preloading-strategy';
// import { ObservableModule } from './observable/observable.module';
// import { PromiseModule } from './promise/promise.module';


const routes: Routes = [
  /**
   * 1. Lazy loaded routes, default page, and non-matching pages (denoted by path "**") are declared here.
   * 2. eager load routes are not declared in this module, but declared in their own sub-modules, except for default page.
   * 3. "home module (default)" is DEFAULT page. Therefore, it IS declared here.
   * 4. "contact module" is eager loading. Therefore, its path IS NOT declared here. It is declared in its own sub-module.
   * 5. Eagerly loaded modules' constructors and routers' constructors will be called at
   *    initial access of the website. This can be a proof that the module is eager loaded.
   */
  {
    /**
     * Lazy loading.
     *
     * path: 'Providen lazy path here'
     * loadChildren: () => import('./my-http/my-http.module').then(m => m.MyHttpModule)
     * loadChildren: 'prefer expression to string literal for type safe.'
     * loadChildren: './my-http/my-http.module#MyHttpModule' --- String literal is NOT type safe!
     *
     * Angular 7 static import:
     * loadChildren: () => MyHttpModule
     * Angular 8 dynamic import:
     * loadChildren: () => import('./my-http/my-http.module').then(m => m.MyHttpModule)
     */
    path: 'window-size',
    loadChildren: () => import('./window-size/window-size.module').then(m => m.WindowSizeModule)
  },
  {
    /**
     * Default page --- Eager Loading
     */
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      routes,
      {
        preloadingStrategy: SelectivePreloadingStrategy,
        onSameUrlNavigation: 'reload',
        scrollPositionRestoration: 'top',
        /**
         * `useHash: true` allows page refresh using browser "Reload" button for page refresh.
         * `useHash: false` disables page refresh using browser "Reload" button for page refresh.
         */
        useHash: true
      }
    )
  ],
  exports: [
    RouterModule
  ],
  providers: [
    SelectivePreloadingStrategy
  ]
})
export class AppRoutingModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('AppRoutingModule constructor.');
  }
}
