import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

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
         * loadChildren: () => import('./my-primeng-table/my-primeng-table.module').then(m => m.MyPrimengTableModule)
         * loadChildren: 'prefer expression to string literal for type safe.'
         * loadChildren: './my-http/my-http.module#MyHttpModule' --- String literal is NOT type safe!
         *
         * Angular 7 static import:
         * loadChildren: () => MyHttpModule
         * Angular 8 dynamic import:
         * loadChildren: () => import('./my-primeng-table/my-primeng-table.module').then(m => m.MyPrimengTableModule)
         */
        path: 'login',
        loadChildren: () => import('./login/login.module').then(m => m.LoginModule)
    },
    {
        /**
         * Lazy loading.
         */
        path: 'home',
        /**
         * Angular 8 dynamic import
         */
        loadChildren: () => import('./home/home.module').then(m => m.HomeModule)
    },
    {
        /**
         * Default page --- Eager Loading
         */
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
    },
    {
        path: '**',
        redirectTo: 'login',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [
        RouterModule.forRoot(
            routes,
            {
                onSameUrlNavigation: 'reload',
                useHash: true
            }
        )
    ],
    exports: [
        RouterModule
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
