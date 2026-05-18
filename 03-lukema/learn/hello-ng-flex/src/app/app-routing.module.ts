import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFoundComponent } from './page-not-found.component';
import { SelectivePreloadingStrategy } from './selective-preloading-strategy';

const routes: Routes = [
    {
        path: 'my-flex',
        loadChildren: () => import('./my-flex/my-flex.module').then(m => m.MyFlexModule)
    },
    {
        path: 'center',
        loadChildren: () => import('./center/center.module').then(m => m.CenterModule)
    },
    {
        path: '',
        redirectTo: 'my-flex',
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
export class AppRoutingModule { }