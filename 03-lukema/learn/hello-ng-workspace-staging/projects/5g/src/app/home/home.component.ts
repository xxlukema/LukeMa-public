import { Component, Inject, OnInit } from '@angular/core';
import { SessionStorage } from 'ngx-webstorage';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

    constructor(
      ) {
        console.log('home.component', 'constructor');
    }

    @SessionStorage('fullname') fullname: string | undefined;

    ngOnInit(): void {
        console.log('home.component', 'ngOnInit');
    }

}
