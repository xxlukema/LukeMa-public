import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ObservableRoutingModule } from './observable-routing.module';
import { ObservableComponent } from './observable.component';


@NgModule({
    declarations: [ObservableComponent],
    imports: [
        CommonModule,
        ObservableRoutingModule
    ]
})
export class ObservableModule {

    /**
     * Optional. For debugging lazy routing only.
     */
    constructor() {
        console.log('ObservableModule constructor.');
    }
}
