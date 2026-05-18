import { Component, Input } from '@angular/core';
import { AppInjectable } from '../app.injectable';

@Component({
    selector: 'app-contact-child2',
    templateUrl: './contact-child2.component.html',
    styleUrls: ['contact-child2.component.css']
})

export class ContactChild2Component {
    @Input()
    child1TextValue: string;

    constructor(public appInjectable: AppInjectable) { }
}
