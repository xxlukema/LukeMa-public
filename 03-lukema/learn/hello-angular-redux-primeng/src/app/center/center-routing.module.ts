import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CenterComponent } from './center.component';

const routes: Routes = [
    {
        // path: 'my-primeng', // If path value is not empty. It is eager load.
        path: '', // If path value is empty. It is lazy load. Lazy loaded feature's vavigate path is configured in app-routing.module.ts
        component: CenterComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CenterRoutingModule {
    /**
     * Optional. For debugging lazy routing only.
     */
    constructor() {
        console.log('CenterRoutingModule constructor.');
    }
}
