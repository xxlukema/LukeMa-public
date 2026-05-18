import { Component, Inject, OnInit } from '@angular/core';
import { APP_CONFIG, MY_CONFIG } from '@luke/my-conf-lib';
import { SessionStorage, SessionStorageService } from 'ngx-webstorage';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

    constructor(
        @Inject(APP_CONFIG) private appConfig: any,
        @Inject(MY_CONFIG) private myConfig: any,
        private sessionStorageService: SessionStorageService) {

        console.log('home.component', 'constructor', 'Injection of @Inject(APP_CONFIG) and @Inject(MY_CONFIG). They are injected whenever it is needed.');
    }

    @SessionStorage('fullname', 'Tom Wang') fullname: string | undefined;

    ngOnInit(): void {
        console.log('home.component', 'ngOnInit', 'appConfig', this.appConfig, 'myConfig', this.myConfig);

        if (!this.fullname) {
            console.log('fullname is not set.');

            this.fullname = this.sessionStorageService.retrieve('fullname');

        }

        console.log('Fullname', this.fullname);
    }

}
