import { Component, OnDestroy, OnInit } from '@angular/core';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {

  title = 'Hello Angular';

  constructor() { }

  /**
   * Called whenever entering the page/template.
   */
  ngOnInit() {
    console.log('AppComponent ngOnInit() called.');
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy() {
    console.log('AppComponent ngOnDestroy() called.');
  }

}
