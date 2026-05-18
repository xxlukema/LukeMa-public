import { Directive, ElementRef, HostListener, Renderer2, OnInit } from '@angular/core';

@Directive({
    selector: '[appHostlistener]'
})
export class AppHostListenerDirective implements OnInit {

    constructor(private el: ElementRef, private renderer: Renderer2) { }

    /**
     * Host Listeners are event listeners attached to any element that hosts (the directive is placed on) the directive.
     */
    @HostListener('mouseenter') onMouseEnter() {
        this.highlight('yellow');
    }

    @HostListener('mouseleave') onMouseLeave() {
        this.highlight(null);
    }

    private highlight(color: string) {
        this.el.nativeElement.style.backgroundColor = color;
    }

    ngOnInit() {
    }
}
