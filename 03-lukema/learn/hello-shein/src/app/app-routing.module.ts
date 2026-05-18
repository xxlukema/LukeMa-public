import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
// import { MyHttpModule } from './my-http/my-http.module';
// import { MyMatProgressBarModule } from './my-mat-progress-bar/my-mat-progress-bar.module';
import { PageNotFoundComponent } from './page-not-found.component';
import { SelectivePreloadingStrategy } from './selective-preloading-strategy';
// import { ObservableModule } from './observable/observable.module';
// import { PromiseModule } from './promise/promise.module';


/**
 * [Demystifying Angular Route Guards: A Beginner's Guide to Secure Navigation]
 * <https://dev.to/this-is-angular/demystifying-angular-route-guards-a-beginners-guide-to-secure-navigation-597b>
 * (Sample Inlined Guard) Preventing a user to leave the page can be as simple as that:
 *     canDeactivate: [() => !inject(SignInComponent).registrationForm.touched]
 * Note: A drawback of this way of writing your guards is that you won't be able to unit test it!
 */
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
     * Lazy loading.
     */
    path: 'productadd',
    /**
     * Angular 8 dynamic import
     */
    loadChildren: () => import('./product/productadd/productadd.module').then(m => m.ProductaddModule)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'productlist',
    /**
     * Angular 8 dynamic import
     */
    loadChildren: () => import('./product/productlist/productlist.module').then(m => m.ProductlistModule)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'productdetail',
    /**
     * Angular 8 dynamic import
     */
    loadChildren: () => import('./product/productdetail/productdetail.module').then(m => m.ProductdetailModule)
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
    path: 'signin',
    /**
     * Angular 16 standalone
     */
    loadComponent: () => import('./user/signin/signin.component').then(c => c.SigninComponent)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'register',
    /**
     * Angular 16 standalone
     */
    loadComponent: () => import('./user/register/register.component').then(c => c.RegisterComponent)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'item',
    /**
     * Angular 16 standalone
     */
    loadComponent: () => import('./item/item.component').then(c => c.ItemComponent)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'checkout',
    /**
     * Angular 16 standalone
     */
    loadComponent: () => import('./sell/checkout/checkout.component').then(c => c.CheckoutComponent)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'home',
    /**
     * Angular 16 standalone
     */
    loadComponent: () => import('./home/home.component').then(c => c.HomeComponent)
  },
  {
    /**
     * Lazy loading.
     */
    path: 'sell',
    /**
     * Angular 16 standalone
     */
    children: [
      {
        path: '',
        loadComponent: () => import('./sell/sell.component').then(c => c.SellComponent),
        data: {
          title: 'Listing'
        },
      },
      {
        path: 'list',
        loadComponent: () => import('./sell/listing/listing.component').then((c) => c.ListingComponent),
        data: {
          title: 'Listing'
        },
      },
      {
        path: 'findmatch',
        loadComponent: () => import('./sell/find-match/find-match.component').then((c) => c.FindMatchComponent),
        data: {
          title: 'Find Match'
        },
      },
      {
        path: 'draft',
        loadComponent: () => import('./sell/draft/draft.component').then((c) => c.DraftComponent),
        data: {
          title: 'Draft'
        },
      },
      {
        path: 'selling',
        loadComponent: () => import('./sell/selling/selling.component').then((c) => c.SellingComponent),
        data: {
          title: 'Selling'
        },
      },
      {
        path: 'buyitnow',
        loadComponent: () => import('./sell/buyitnow/buyitnow.component').then(c => c.BuyitnowComponent),
        data: {
          title: 'Buy It Now'
        },
      },
    ]
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
        scrollPositionRestoration: 'enabled', /** top */
        /**
         * `useHash: true` allows page refresh using browser "Reload" button for page refresh.
         * `useHash: false` disables page refresh using browser "Reload" button for page refresh.
         */
        useHash: true,
        anchorScrolling: 'enabled'
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
