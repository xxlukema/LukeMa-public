import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { BlockUiModule } from '@/app/utils/blockui/blockui.module';
import { LoadingModule } from '@/app/utils/loading/loading.module';
import { ProductdetailRoutingModule } from './productdetail-routing.module';
import { ProductdetailComponent } from './productdetail.component';
import { ProductdetailService } from './productdetail.service';


@NgModule({
  imports: [
    ProductdetailRoutingModule,
    CommonModule,
    MatCardModule,
    FlexLayoutModule,
    MatButtonModule,
    MatInputModule,
    FormsModule,
    BlockUiModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    LoadingModule
  ],
  declarations: [ProductdetailComponent],
  providers: [
    ProductdetailService
  ]
})
export class ProductdetailModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('ProductdetailModule constructor.');
  }
}
