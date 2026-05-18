import { Component, HostBinding } from '@angular/core';
import { slideInRightAnimation } from '../animations/animations';
import { UserService } from '../service/user.service';


@Component({
  templateUrl: './admin.component.html',
  animations: [slideInRightAnimation]
})
export class AdminComponent {

  @HostBinding('@routeAnimation') routeAnimation = true;
  @HostBinding('style.display') display = 'block';
  // @HostBinding('style.position') position = 'absolute';

  /**
   * @param userService - Will be used in template.
   */
  constructor(public userService: UserService) {
  }
}
