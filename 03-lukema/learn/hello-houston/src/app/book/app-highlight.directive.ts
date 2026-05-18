import { Directive, ElementRef, Input, Renderer2, OnInit } from '@angular/core';


@Directive({
    selector: '[appHighlight]'
})
export class AppHighlightDirective implements OnInit {

    @Input() appHighlight: string;

    constructor(private el: ElementRef, private renderer: Renderer2) { }

    /**
     * If put this block into constructor it will not work.
     * We have to move the implementation from the constructor to ngOnInit lifecycle method
     * because myhidden property will be set late. ngOnInit will wait for all initialization
     * processes to be complete before executing.
     */
    ngOnInit() {

        // console.log('appHighlight=' + this.appHighlight);

        if (this.appHighlight) {
            this.el.nativeElement.style.backgroundColor = this.appHighlight;
        } else {
            this.el.nativeElement.style.backgroundColor = 'yellow';
        }
    }
}

