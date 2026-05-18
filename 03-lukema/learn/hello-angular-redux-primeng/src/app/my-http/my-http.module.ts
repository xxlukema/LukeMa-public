import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MyHttpRoutingModule } from './my-http-routing.module';
import { MyHttpComponent } from './my-http.component';

@NgModule({
    imports: [
        /** async pipe is in CommonModule */
        CommonModule,
        MyHttpRoutingModule
    ],
    declarations: [
        MyHttpComponent
    ]
})
export class MyHttpModule {

    /**
     * Optional. For debugging lazy routing only.
     */
    constructor() {
        console.log('MyHttpModule constructor.');
    }
}
