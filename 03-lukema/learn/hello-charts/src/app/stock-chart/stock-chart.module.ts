import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { NgChartsModule } from 'ng2-charts';
import { NgxMaskModule } from 'ngx-mask';
import { StockChartRoutingModule } from './stock-chart-routing.module';
import { StockChartComponent } from './stock-chart.component';

@NgModule({
    declarations: [StockChartComponent],
    imports: [
        CommonModule,
        FormsModule, /** FormsModule is needed to fix: Can't bind to 'ngModel' since it isn't a known property of 'input' */
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatButtonModule,
        FlexLayoutModule,
        NgxMaskModule,
        MatDatepickerModule,
        MatNativeDateModule,
        ReactiveFormsModule,
        NgChartsModule,
        StockChartRoutingModule
    ]
})
export class StockChartModule { }
