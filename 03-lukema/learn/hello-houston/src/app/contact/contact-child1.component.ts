import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AppInjectable } from '../app.injectable';

@Component({
    selector: 'app-contact-child1',
    templateUrl: './contact-child1.component.html',
    styleUrls: ['contact-child1.component.css']
})
export class ContactChild1Component {
    @Input()
    parentKeyStrikesValue: string;

    @Output()
    child1TextChanged = new EventEmitter<string>();

    constructor(public appInjectable: AppInjectable) { }

    onChange1(value: string) {
        this.child1TextChanged.emit(value);
    }
}
