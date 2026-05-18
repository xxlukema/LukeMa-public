import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { MyInjectable } from './my-injectable';

@Component({
    selector: 'app-injectable',
    templateUrl: './injectable.component.html',
    styleUrls: ['./injectable.component.scss']
})
export class InjectableComponent implements OnInit {

    constructor(public myInjectable: MyInjectable,
        private domSanitizer: DomSanitizer) { }

    originalHtml = `
    <section style="border: 2px dashed blue;">
       <h4>My innerHTML</h4>
       <div style="color: red; background-color: lime;">This is my Text.</div>
    </section>
    `;

    myHtml = this.domSanitizer.bypassSecurityTrustHtml(this.originalHtml);

    ngOnInit(): void {
    }

    add() {
        this.myInjectable.globalNumber = this.myInjectable.globalNumber + 1;
    }

    deduct() {
        this.myInjectable.globalNumber = this.myInjectable.globalNumber - 1;
    }

}
