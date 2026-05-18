import { Component } from '@angular/core';

@Component({
    selector: 'app-contact-parent',
    templateUrl: './contact-parent.component.html',
    styleUrls: ['contact-parent.component.css']
})

export class ContactParentComponent {
    child1TextValue: string;
    myChild1TextValue: string;
    parentInputValue = 'parentInput init value';

    myChild1TextValueChanged($event: string) {
        console.log('myChild1TextValueChanged', $event);
    }
}
