import { Pipe, PipeTransform, SecurityContext } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Pipe({
    name: 'byPassSecurity',
})
export class ByPassSecurityPipe implements PipeTransform {
    constructor(private domSanitizer: DomSanitizer) { }

    transform(value: string): SafeHtml {
        // return this.domSanitizer.sanitize(SecurityContext.HTML, this.domSanitizer.bypassSecurityTrustHtml(value));
        // return this.domSanitizer.sanitize(SecurityContext.HTML, value);
        return this.domSanitizer.bypassSecurityTrustHtml(value);
    }

}
