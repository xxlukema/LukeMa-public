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
    path: 'my-http',
    loadChildren: () => import('./my-http/my-http.module').then(m => m.MyHttpModule)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'flux-demo',
    /**
     * Angular 8 dynamic import
     */
    loadChildren: () => import('./flux-demo/flux-demo.module').then(m => m.FluxDemoModule)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'my-mat-progress-bar',
    /**
     * Angular 7 static import:
     * loadChildren: () => MyMatProgressBarModule,
     */
    loadChildren: () => import('./my-mat-progress-bar/my-mat-progress-bar.module').then(m => m.MyMatProgressBarModule)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'my-mat-table',
    /**
     * Angular 7 static import:
     * loadChildren: () => MyMatProgressBarModule,
     */
    loadChildren: () => import('./my-mat-table/my-mat-table.module').then(m => m.MyMatTableModule)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'my-google-map',
    /**
     * Angular 7 static import:
     * loadChildren: () => MyMatProgressBarModule,
     */
    loadChildren: () => import('./my-google-map/my-google-map.module').then(m => m.MyGoogleMapModule)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'observable',
    loadChildren: () => import('./observable/observable.module').then(m => m.ObservableModule)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'promise',
    loadChildren: () => import('./promise/promise.module').then(m => m.PromiseModule)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'file-upload-download',
    loadChildren: () => import('./file-upload/file-upload.module').then(m => m.FileUploadModule)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'modal-dialog-demo',
    loadChildren: () => import('./modal-dialog-demo/modal-dialog-demo.module').then(m => m.ModalDialogDemoModule)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'injectable',
    loadChildren: () => import('./injectable/injectable.module').then(m => m.InjectableModule)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'window-size',
    loadChildren: () => import('./window-size/window-size.module').then(m => m.WindowSizeModule)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'flux2-demo',
    loadChildren: () => import('./flux2-demo/flux2-demo.module').then(m => m.Flux2DemoModule)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'center',
    loadChildren: () => import('./center/center.module').then(m => m.CenterModule)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'fieldset',
    loadChildren: () => import('./fieldset/fieldset.module').then(m => m.FieldsetModule)
  },
  /**
  {
    path: 'my-leaflet',
    loadChildren: () => import('./my-leaflet/my-leaflet.module').then(m => m.MyLeafletModule)
  },
  */
  {
    /**
     * Lazy loading.
     */
    path: 'whidbey2',
    loadChildren: () => import('./whidbey2/whidbey2.module').then(m => m.Whidbey2Module)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'form',
    loadChildren: () => import('./form/form.module').then(m => m.FormModule)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'events',
    loadChildren: () => import('./my-event/my-event.module').then(m => m.MyEventModule)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'my-observ',
    loadChildren: () => import('./my-observable/my-observable.module').then(m => m.MyObservableModule)
  },
  {
    /**
     * Default page --- Eager Loading
     */
    path: '',
    redirectTo: '/home',
    // redirectTo: '/my-leaflet',
    // redirectTo: '/whidbey2',
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
