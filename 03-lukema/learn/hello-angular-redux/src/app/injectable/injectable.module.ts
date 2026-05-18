import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { InjectableRoutingModule } from './injectable-routing.module';
import { InjectableComponent } from './injectable.component';

@NgModule({
  declarations: [InjectableComponent],
  imports: [
    CommonModule,
    InjectableRoutingModule
  ],
  providers: [
    // { provide: DomSanitizer, useValue: { bypassSecurityTrustHtml: () => 'safeString' } },
  ]
})
export class InjectableModule { }
