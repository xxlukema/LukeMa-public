import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
    title = 'my-properties-ng';

    ngOnInit() {
        // console.log('------------- 222222 location port', location.port);
        // console.log('------------- 222222 location port', location.port === '4200');
    }

}
