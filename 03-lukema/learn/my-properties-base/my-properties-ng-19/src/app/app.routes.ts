import { Routes } from '@angular/router';
import { PageNotFoundComponent } from './page-not-found.component';

export const routes: Routes = [
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
    loadComponent: () => import('./property-report/property-report.component').then(m => m.PropertyReportComponent)
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
    path: 'input',
    loadComponent: () => import('./input/input.component').then((m) => m.InputComponent)
  },
  {
    path: '**',
    component: PageNotFoundComponent,
  },
];
