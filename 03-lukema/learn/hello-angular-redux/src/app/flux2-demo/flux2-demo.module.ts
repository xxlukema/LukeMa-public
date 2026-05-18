import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { Flux2DemoRoutingModule } from './flux2-demo-routing.module';
import { Flux2DemoComponent } from './flux2-demo.component';

@NgModule({
    declarations: [Flux2DemoComponent],
    imports: [
        CommonModule,
        Flux2DemoRoutingModule
    ]
})
export class Flux2DemoModule {

    /**
     * Optional. For debugging lazy routing only.
     */
    constructor() {
        console.log('Flux2DemoModule constructor.');
    }
}
