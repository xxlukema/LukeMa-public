import { HttpErrorResponse } from '@angular/common/http';
import { Component, HostBinding, OnDestroy, OnInit, AfterViewInit  } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { slideInDownAnimation } from '../animations/animations';
import { User, UserService } from '../service/user.service';


@Component({
  templateUrl: './login.component.html',
  animations: [slideInDownAnimation]
})
export class LoginComponent implements OnInit, OnDestroy  {
  @HostBinding('@routeAnimation') routeAnimation = true;
  @HostBinding('style.display') display = 'block';
  // @HostBinding('style.position') position = 'absolute';

  message: string;

  username: string;
  password: string;
  keepMeLoggedIn = true;

  // Set our navigation extras object
  // that passes on our global query params and fragment
  private navigationExtras: NavigationExtras = {
    queryParamsHandling: 'merge',
    preserveFragment: true
  };

  constructor(public userService: UserService, public router: Router) {
  }

  ngOnInit(): void {
    console.log('LoginComponent ngOnInit() username: ' + this.userService.user.username);
    this.message = 'This message is set before userService completes login process: this.userService.isLoggedIn=' + this.userService.isLoggedIn;
  }

  ngOnDestroy() {
    console.log('LoginComponent ngOnDestroy() username: ' + this.userService.user.username);
  }

  loginAndRedirectToAdmin() {

    console.log(this.username + ' ' + this.password + ' ' + this.keepMeLoggedIn);

    if (this.username == null || this.password == null) {
      return;
    }

    const tmpUser: User = new User();
    tmpUser.username = this.username;
    tmpUser.password = this.password;

    this.userService.loginUserWithObservable(tmpUser).subscribe(
      (user: User) => {

        console.log('LoginComponent.loginAndRedirectToAdmin() loginUserWithObservable received: ' + user);

        if (user.id === -1) {
          this.router.navigate(['/signup'], this.navigationExtras);
        } else if (user.id === -2) {
          this.message = 'Incorrect Password.';
        } else {
          this.userService.user = user;

          if (this.keepMeLoggedIn) {
            this.userService.saveUserToLocalStorage(user);
          }

          this.router.navigate(['/admin'], this.navigationExtras);
        }
      },
      (err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          console.log('Client-side error occured: ' + err.error);
        } else {
          console.log('Server-side error occured: ' + err.error);
        }
      }
    );
  }

  logout() {
    this.userService.logout();
  }
}
