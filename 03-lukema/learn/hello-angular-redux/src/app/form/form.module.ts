import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
// import { ErrorStateMatcher } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { NgxMaskConfig, provideEnvironmentNgxMask } from 'ngx-mask';
import { LoadingModule } from '../loading/loading.module';
// import { MyErrorStateMatcher } from '../utils/my-error-state.matcher';
import { FormRoutingModule } from './form-routing.module';
import { FormComponent } from './form.component';

const maskConfig: Partial<NgxMaskConfig> = {
  validation: false,
};

@NgModule({
  declarations: [
    FormComponent
  ],
  imports: [
    CommonModule,
    FormRoutingModule,
    MatRadioModule,
    MatSelectModule,
    MatCardModule,
    FlexLayoutModule,
    MatButtonModule,
    MatInputModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    LoadingModule
  ],
  providers: [
    // { provide: ErrorStateMatcher, useClass: MyErrorStateMatcher }
    provideEnvironmentNgxMask(maskConfig),
  ]
})
export class FormModule { }
