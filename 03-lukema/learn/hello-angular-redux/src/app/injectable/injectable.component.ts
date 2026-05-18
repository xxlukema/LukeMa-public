import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { MyInjectable } from './my-injectable';

@Component({
  standalone: false,
  selector: 'app-injectable',
  templateUrl: './injectable.component.html',
  styleUrls: ['./injectable.component.scss']
})
export class InjectableComponent implements OnInit {

  constructor(public myInjectable: MyInjectable,
    private readonly domSanitizer: DomSanitizer) { }

  originalHtml = `
    <section style="border: 2px dashed blue;">
       <h4>My innerHTML</h4>
       <div style="color: red; background-color: lime;">This is my Text.</div>
    </section>
    `;

  myHtml: SafeHtml = {};

  /**
   * Wrong! ==> Will get "TypeError: this.domSanitizer is undefined"
   *
   * 'private readonly domSanitizer: DomSanitizer' is injected in constructor. At membership initilization stage, 'this.domSanitizer' has not
   * been injected yet.
   *
   * Solution: Do this inside ngOnInit()
   */
  // myHtml = this.domSanitizer.bypassSecurityTrustHtml(this.originalHtml);

  ngOnInit(): void {
    /**
     * Correct: access 'this.domSanitizer' inside 'ngOnInit()' to avoid "TypeError: this.domSanitizer is undefined"
     */
    this.myHtml = this.domSanitizer.bypassSecurityTrustHtml(this.originalHtml);
  }

  add() {
    this.myInjectable.globalNumber = this.myInjectable.globalNumber + 1;
  }

  deduct() {
    this.myInjectable.globalNumber = this.myInjectable.globalNumber - 1;
  }

}
