import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'hello-protractor';

  ngOnInit() {
    console.log('app.componenet', 'Click "Login" button to see home page and appConfig values');
  }

}
