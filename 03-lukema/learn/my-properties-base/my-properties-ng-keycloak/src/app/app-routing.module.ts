import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFoundComponent } from './page-not-found.component';
import { SelectivePreloadingStrategy } from './selective-preloading-strategy';

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
     * loadChildren: () => import('./property-report/property-report.module').then(m => m.PropertyReportModule)
     * loadChildren: 'prefer expression to string literal for type safe.'
     * loadChildren: './property-report/property-report.module#PropertyReportModule' --- String literal is NOT type safe!
     *
     * Angular 7 static import:
     * loadChildren: () => PropertyReportModule
     *
     * Compare:
     *
     * Angular 8 dynamic import:
     * loadChildren: () => import('./property-report/property-report.module').then(m => m.PropertyReportModule)
     */
    path: 'property-report',
    data: {
      params: {
        user: 'init',
      },
    },
    loadChildren: () =>
      import('./property-report/property-report.module').then(
        (m) => m.PropertyReportModule
      ),
  },
  {
    /**
     * Default page --- Change to a Eager Loading page
     */
    path: '',
    redirectTo: '/property-report?user=luke',
    pathMatch: 'full',
  },
  {
    /**
     * Keycloak response
     */
    path: 'session_state',
    redirectTo: '/property-report?user=luke',
    pathMatch: 'full',
  },
  {
    path: 'input',
    loadChildren: () =>
      import('./input/input.module').then((m) => m.InputModule),
  },
  {
    path: 'login',
    loadChildren: () =>
      import('./login/login.module').then((m) => m.LoginModule),
  },
  {
    path: 'logout',
    loadChildren: () =>
      import('./logout/logout.module').then((m) => m.LogoutModule),
  },
  {
    path: '**',
    component: PageNotFoundComponent,
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      preloadingStrategy: SelectivePreloadingStrategy,
      onSameUrlNavigation: 'reload',
      scrollPositionRestoration: 'top',
      useHash: true,
    }),
  ],
  exports: [RouterModule],
  providers: [SelectivePreloadingStrategy],
})
export class AppRoutingModule {
  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('AppRoutingModule constructor.');
  }
}
