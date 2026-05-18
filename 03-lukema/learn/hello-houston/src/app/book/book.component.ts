import { Component, HostBinding } from '@angular/core';
import { slideInRightAnimation } from '../animations/animations';


@Component({
  templateUrl: './book.component.html',
  animations: [slideInRightAnimation]
})

export class BookComponent {
  @HostBinding('@routeAnimation') routeAnimation = true;
  @HostBinding('style.display') display = 'block';
  // @HostBinding('style.position') position = 'absolute';

}
