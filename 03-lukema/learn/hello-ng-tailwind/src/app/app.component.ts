import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { NavComponent } from './nav/nav.component';
import { ToggleDarkModeComponent } from './toggle-dark-mode/toggle-dark-mode.component';


@Component({
  selector: 'app-root',
  imports: [
    HeaderComponent,
    FooterComponent,
    NavComponent,
    ToggleDarkModeComponent,
    RouterOutlet,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {

  constructor(
  ) {
    console.log('AppComponent constructor');
  }

  title = 'hello-ng-tailwind';

  /**
   * For testing purposes only
   */
  num1 = 1;
  num2 = 2;

  loading = false;

  /**
   * For testing purposes only
   */
  add(a: number, b: number) {
    return a + b;
  }

  /**
   * For testing purposes only
   */
  subtractNum2ByNum1() {
    return this.num2 - this.num1;
  }

}
