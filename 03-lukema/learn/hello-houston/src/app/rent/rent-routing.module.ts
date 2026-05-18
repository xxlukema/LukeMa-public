import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { RentComponent } from './rent.component';
import {AddRentPropertyComponent} from './add-rent-property.component';

const routes: Routes = [
    {
        path: 'rent',
        component: RentComponent,
        data: { email: 'x.luke.ma@gmail.com' }
    },
    {
        path: 'addRentProperty',
        component: AddRentPropertyComponent,
        data: { email: 'x.luke.ma@gmail.com' }
    }
];

@NgModule( {
    imports: [
        RouterModule.forChild( routes )
    ],
    exports: [
        RouterModule
    ]
} )
export class RentRoutingModule { }
