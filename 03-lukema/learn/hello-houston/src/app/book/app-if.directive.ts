import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';

@Directive({
    selector: '[appIf]'
})
export class AppIfDirective {

    constructor(
        private template: TemplateRef<any>,
        private viewContainer: ViewContainerRef
    ) { }

    @Input() set appIf(shouldAdd: boolean) {
        if (shouldAdd) {
            // If condition is true add template to DOM
            this.viewContainer.createEmbeddedView(this.template);
        } else {
            // Else remove template from DOM
            this.viewContainer.clear();
        }
    }
}
