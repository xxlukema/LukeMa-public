import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { InjectableRoutingModule } from './injectable-routing.module';
import { InjectableComponent } from './injectable.component';
import { ByPassSecurityPipe } from '../pipes/by-pass-security.pipe';

@NgModule({
    declarations: [InjectableComponent, ByPassSecurityPipe],
    imports: [
        CommonModule,
        InjectableRoutingModule
    ]
})
export class InjectableModule { }
