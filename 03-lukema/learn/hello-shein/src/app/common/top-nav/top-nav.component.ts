import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule, MatMenuTrigger } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router, RouterModule } from '@angular/router';


@Component({
  selector: 'app-top-nav',
  templateUrl: './top-nav.component.html',
  standalone: true,
  imports: [MatToolbarModule, MatIconModule, RouterModule, MatButtonModule, MatMenuModule],
})
export class TopNavComponent implements OnInit, OnDestroy {
  constructor(public router: Router) { }

  interval$?: NodeJS.Timeout;

  @ViewChild('myAnymals', { read: MatMenuTrigger, static: false }) myAnymalsTrigger!: MatMenuTrigger;

  openMyMenu() {
    if (this.interval$) {
      clearTimeout(this.interval$);
    }
    this.myAnymalsTrigger.openMenu();
  }

  closeMyMenu() {
    this.interval$ = setTimeout(() => {
      this.myAnymalsTrigger.closeMenu();
    }, 200);
  }


  ngOnInit() {
  }


  ngOnDestroy() {
  }

  logout() {
  }

  sendMessage() {

    this.router.navigate(['/submit-case'], {
      queryParams:
      {
        surveyInstanceId: -102030405
      }
    });
  }

}
