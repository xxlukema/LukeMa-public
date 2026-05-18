import { Component, OnInit } from '@angular/core';
import { AppInjectable } from '../app.injectable';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

    constructor(public appInjectable: AppInjectable) { }

    ngOnInit(): void {
    }

}
