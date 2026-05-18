import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FooterModule } from '../footer/footer.module';
import { MinusSignToParensPipe } from '../pipes/minus-sign-to-parens.pipe';
import { PropertyReportRoutingModule } from './property-report-routing.module';
import { PropertyReportComponent } from './property-report.component';

@NgModule({
    declarations: [PropertyReportComponent, MinusSignToParensPipe],
    imports: [
        CommonModule,
        FlexLayoutModule,
        FooterModule,
        PropertyReportRoutingModule
    ],
    providers: []
})
export class PropertyReportModule {
    /**
     * Optional. For debugging lazy routing only.
     */
    constructor() {
        console.log('PropertyReportModule constructor.');
    }
}
