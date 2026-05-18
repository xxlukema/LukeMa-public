import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MyMatTableRoutingModule } from './my-mat-table-routing.module';
import { MyMatTableComponent } from './my-mat-table.component';
import { MyMatTableService } from './my-mat-table.service';


@NgModule({
  declarations: [
    MyMatTableComponent,
  ],
  imports: [
    CommonModule,
    MatTableModule,
    MatInputModule,
    MatFormFieldModule,
    FlexLayoutModule,
    MatPaginatorModule,
    MatSortModule,
    MyMatTableRoutingModule
  ],
  providers: [
    MyMatTableService,
  ]
})
export class MyMatTableModule { }
