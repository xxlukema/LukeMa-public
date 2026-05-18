import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit, Renderer2 } from '@angular/core';


@Component({
  standalone: false,
  selector: 'app-toggle-dark-mode',
  templateUrl: './toggle-dark-mode.component.html',
  styleUrls: ['./toggle-dark-mode.component.scss']
})
export class ToggleDarkModeComponent implements OnInit {

  constructor(@Inject(DOCUMENT) private readonly document: Document, private readonly renderer2: Renderer2) { }

  darkMode = true;

  ngOnInit(): void {
    console.log('ToggleDarkModeComponent ngOnInit() called.');

    this.darkMode = true;
    // this.document.body.style = 'color: white !important; background-color: black !important;';
    this.renderer2.removeClass(this.document.body, 'normal-mode');
    this.renderer2.addClass(this.document.body, 'dark-mode');
  }

  toggleDarkMode = () => {
    console.log('app', 'toggleDarkMode clicked.');
    this.darkMode = !this.darkMode;
    if (this.darkMode) {
      // this.document.body.style = 'color: white !important; background-color: black !important;';
      this.document.body.classList.remove('normal-mode');
      this.document.body.classList.add('dark-mode');
    } else {
      // this.document.body.style = 'color: black !important; background-color: lightgrey !important;';
      this.renderer2.removeClass(this.document.body, 'dark-mode');
      this.renderer2.addClass(this.document.body, 'normal-mode');
    }

    console.log('app', 'this.darkMode', this.darkMode);
  };

}
