import { Component, OnInit, OnDestroy } from '@angular/core';
import { UserService } from './service/user.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'Hello, Houston!';

  constructor(private userService: UserService) {}

  /**
   * AppComponent is the root component. This will be called when load the page.
   */
  ngOnInit(): void {
    console.log('AppComponenet ngOnInit(). this.userService.isLoggedIn? ' + this.userService.isLoggedIn);
  }

  ngOnDestroy() {
    console.log('AppComponenet ngOnDestroy().');
  }
}
