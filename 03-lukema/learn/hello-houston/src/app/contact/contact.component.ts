import { Component, HostBinding, HostListener } from '@angular/core';
import { slideInDownAnimation } from '../animations/animations';


@Component({
  templateUrl: './contact.component.html',
  animations: [slideInDownAnimation]
})
export class ContactComponent {
  @HostBinding('@routeAnimation') routeAnimation = true;
  @HostBinding('style.display') display = 'block';
  // @HostBinding('style.position') position = 'absolute';

  email = 'x.luke.ma@gmail.com';
  phone = '832-588-7811';

  @HostListener('click') click() {
    // window.alert('Host Element Clicked');
    // alert('Host Element Clicked');
    console.log('Host Element Clicked');
  }
}
