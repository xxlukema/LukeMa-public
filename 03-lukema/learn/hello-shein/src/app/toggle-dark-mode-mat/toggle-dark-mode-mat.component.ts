import { OverlayContainer } from '@angular/cdk/overlay';
import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit, Renderer2 } from '@angular/core';


@Component({
  selector: 'app-toggle-dark-mode-mat',
  templateUrl: './toggle-dark-mode-mat.component.html',
  styleUrls: ['./toggle-dark-mode-mat.component.scss']
})
export class ToggleDarkModeMatComponent implements OnInit {

  constructor(
    @Inject(DOCUMENT) private document: Document,
    private overlay: OverlayContainer,
    private renderer2: Renderer2) { }

  darkMode = false;

  ngOnInit(): void {
    console.log('ToggleDarkModeMatComponent ngOnInit() called.');

    if (this.darkMode) {
      this.renderer2.removeClass(this.document.body, 'normalMode');
      this.overlay.getContainerElement().classList.remove('normalMode');

      this.renderer2.addClass(this.document.body, 'darkMode');
      this.overlay.getContainerElement().classList.add('darkMode');
    } else {
      this.renderer2.removeClass(this.document.body, 'darkMode');
      this.overlay.getContainerElement().classList.remove('darkMode');

      this.renderer2.addClass(this.document.body, 'normalMode');
      this.overlay.getContainerElement().classList.add('normalMode');
    }
  }

  toggleDarkMode = () => {
    console.log('app mat', 'toggleDarkMode clicked.');
    this.darkMode = !this.darkMode;

    if (this.darkMode) {
      this.renderer2.removeClass(this.document.body, 'normalMode');
      this.overlay.getContainerElement().classList.remove('normalMode');

      this.renderer2.addClass(this.document.body, 'darkMode');
      this.overlay.getContainerElement().classList.add('darkMode');

      console.log('app mat', 'dark.');
    } else {
      this.renderer2.removeClass(this.document.body, 'darkMode');
      this.overlay.getContainerElement().classList.remove('darkMode');

      this.renderer2.addClass(this.document.body, 'normalMode');
      this.overlay.getContainerElement().classList.add('normalMode');

      console.log('app mat', 'light');
    }

  };

}
