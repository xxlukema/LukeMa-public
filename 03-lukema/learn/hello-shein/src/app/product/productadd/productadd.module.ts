import { BlockUiModule } from '@/app/utils/blockui/blockui.module';
import { LoadingModule } from '@/app/utils/loading/loading.module';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { ProductaddRoutingModule } from './productadd-routing.module';
import { ProductaddComponent } from './productadd.component';
import { ProductaddService } from './productadd.service';


@NgModule({
  imports: [
    ProductaddRoutingModule,
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
  declarations: [ProductaddComponent],
  providers: [
    ProductaddService,
  ]
})
export class ProductaddModule {

  /**
   * Optional. For debugging lazy routing only.
   */
  constructor() {
    console.log('ProductaddModule constructor.');
  }
}
