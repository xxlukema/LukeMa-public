import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TableModule } from 'primeng/table';
import { MyPrimengTableRoutingModule } from './my-primeng-table-routing.module';
import { MyPrimengTableComponent } from './my-primeng-table.component';

@NgModule({
  imports: [
    CommonModule,
    TableModule,
    MyPrimengTableRoutingModule
  ],
  declarations: [MyPrimengTableComponent]
})
export class MyPrimengTableModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('MyPrimengTableModule constructor.');
  }
}
