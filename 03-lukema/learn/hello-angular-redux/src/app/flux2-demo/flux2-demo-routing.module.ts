import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Flux2DemoComponent } from './flux2-demo.component';


const routes: Routes = [
    {
        // path: 'my-http', // If path value is not empty. It is eager load.
        path: '', // If path value is empty. It is lazy load. Lazy loaded feature's vavigate path is configured in app-routing.module.ts
        component: Flux2DemoComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class Flux2DemoRoutingModule {
    /**
     * Optional. For debugging lazy routing only.
     */
    constructor() {
        console.log('Flux2DemoRoutingModule constructor.');
    }
}
