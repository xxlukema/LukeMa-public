import { AfterViewInit, Directive, ElementRef, HostListener, Renderer2 } from '@angular/core';


@Directive({
    selector: '[appChangeBgColor]'
})
export class ChangeBgColorDirective implements AfterViewInit {

    constructor(
        private elementRef: ElementRef,
        private renderer: Renderer2) {
        // this.changeBgColor('red');
    }

    ngAfterViewInit() {
        this.elementRef.nativeElement.style.color = 'blue';
        this.elementRef.nativeElement.style.fontSize = '20px';
    }

    @HostListener('mouseover') onMouseOver() {
        this.changeBgColor('red');
        console.log('Mounse over custom directive.');
    }

    @HostListener('click') onClick() {
        window.alert('Host Element Clicked');
    }

    @HostListener('mouseleave') onMouseLeave() {
        this.changeBgColor('black');
    }

    changeBgColor(color: string) {
        // this.renderer.setElementStyle(this.elementRef.nativeElement, 'backgroundcolor', color);
        // this.elementRef.nativeElement.setElementStyle('backgroundcolor', 'grey');
    }

}
