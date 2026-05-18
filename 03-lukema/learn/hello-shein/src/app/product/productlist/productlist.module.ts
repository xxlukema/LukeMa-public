import { ProductaddModule } from '@/app/product/productadd/productadd.module';
import { LoadingModule } from '@/app/utils/loading/loading.module';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { ProductlistRoutingModule } from './productlist-routing.module';
import { ProductlistComponent } from './productlist.component';
import { ProductlistService } from './productlist.service';


@NgModule({
  imports: [
    ProductlistRoutingModule,
    CommonModule,
    FlexLayoutModule,
    MatButtonModule,
    ProductaddModule,
    MatPaginatorModule,
    MatSortModule,
    MatCardModule,
    MatSelectModule,
    MatIconModule,
    MatInputModule,
    LoadingModule,
    MatTableModule
  ],
  declarations: [ProductlistComponent],
  providers: [
    ProductlistService
  ]
})
export class ProductlistModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('ProductlistModule constructor.');
  }
}
