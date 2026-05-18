import { Component, OnInit } from '@angular/core';
import {
  Router,
  NavigationExtras
} from '@angular/router';

import { User, UserService } from '../service/user.service';

@Component({
  templateUrl: './signup.component.html'
})
export class SigninComponent implements OnInit {
  message: string;

  private redirectUrl = '/home';

  username2: string;
  password2: string;
  email2: string;
  phone2: string;

  constructor(public userService: UserService, public router: Router) {
  }

  ngOnInit(): void {
    this.userService.pingUserWithPromise().then(
      user => {
        this.message = 'Ping user promise Success! ' + JSON.stringify(user);
      },
      error => {
        this.message = 'Ping user promise Error! ' + JSON.stringify(<any>error);
      }
    );
  }

  loginAndRedirectToAdmin() {

    this.userService.addUserWithObservable(this.userService.user).subscribe(

      (user: User) => {
        console.log('The subscriber sees: ' + JSON.stringify(user));
        this.message = 'Received REST response: ' + JSON.stringify(user);
        console.log('***********: ' + JSON.stringify(this.userService.user));

        // Set our navigation extras object
        // that passes on our global query params and fragment
        const navigationExtras: NavigationExtras = {
          queryParamsHandling: 'merge',
          preserveFragment: true
        };

        // Redirect the user
        this.router.navigate([this.redirectUrl], navigationExtras);
      },
      error => {
        const msg = JSON.stringify(<any>error);
        console.log(msg);
        this.message = msg;
      }
    );
  }
}
