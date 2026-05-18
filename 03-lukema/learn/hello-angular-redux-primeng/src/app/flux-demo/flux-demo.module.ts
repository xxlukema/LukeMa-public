import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FluxDemoRoutingModule } from './flux-demo-routing.module';
import { FluxDemoComponent } from './flux-demo.component';

@NgModule({
    declarations: [FluxDemoComponent],
    imports: [
        CommonModule,
        FluxDemoRoutingModule
    ]
})
export class FluxDemoModule {

    /**
     * Optional. For debugging lazy routing only.
     */
    constructor() {
        console.log('FluxDemoModule constructor.');
    }
}
