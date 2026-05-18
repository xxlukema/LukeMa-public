import { EventService, MyEvent } from '@/app/utils/services/event.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit, ViewChild, inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatMenuTrigger } from '@angular/material/menu';
import { NavigationStart, Router } from '@angular/router';
import { Subject, filter, takeUntil } from 'rxjs';
import { AppService } from './app.service';
import { DarkModeDialogComponent } from './dark-mode-dialog/dark-mode-dialog.component';
import { BlockUiService } from './utils/blockui/blockui.service';
import { JwtHeaderService } from './utils/services/jwt-header.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {

  constructor(
    public router: Router,
    private eventService: EventService,
    private blockUiService: BlockUiService,
    private jwtHeaderService: JwtHeaderService,
    private dialog: MatDialog) {
  }

  private readonly destroyed$ = new Subject<void>();

  appService = inject(AppService);

  /**
   * same as constructor injection.
   */
  // jwtCookieService = inject(JwtCookieService);

  title = 'hello-shein';

  isInSellingPage = false;

  loading = false;

  lastname: string | null | undefined = '';

  username?: string | null | undefined;
  firstname?: string | null | undefined;
  jwtToken?: string | null | undefined;

  staySignedIn?: boolean;

  ngOnInit() {
    this.username = localStorage.getItem('username');
    this.firstname = localStorage.getItem('firstname');

    console.debug('AppComponent ngOnInit() 111:', 'username:', this.username, 'firstname:', this.firstname);

    this.isInSellingPage = false;

    this.router.events
      .pipe(
        filter((event) => event instanceof NavigationStart),
        takeUntil(this.destroyed$),
      )
      .subscribe((event) => {
        // handle navigation start event
        console.debug('------------- nav event', event['url']);

        if (!this.jwtHeaderService.isTokenExpired() && this.jwtHeaderService.getUsername() === this.username) {
          this.firstname = this.jwtHeaderService.getFirstname();
          this.lastname = this.jwtHeaderService.getLastname();
          this.username = this.jwtHeaderService.getUsername();
        } else {
          this.jwtHeaderService.clearToken();
        }

        console.debug('AppComponent ngOnInit() 222:', 'username:', this.username, 'firstname:', this.firstname);

        if (event['url'].includes('/sell')) {
          if (!this.username) {

            const timeoutInSeconds = 1;

            this.blockUiService.lblock(timeoutInSeconds, 'You need to signin to sell an item. Forwarding to signin page...', false);

            setTimeout(() => {
              this.blockUiService.unblock();
              this.router.navigate(['/signin']);
            }, timeoutInSeconds * 1_000);
          }

          this.isInSellingPage = true;
        } else {
          this.isInSellingPage = false;
        }

      });

    this.eventService.eventListener().pipe(
      takeUntil(this.destroyed$)
    ).subscribe({
      next: (event: MyEvent) => {
        console.debug('AppComponent event: ', event);

        /**
         * KEEP! Do NOT remove.
         *
         * If 'event.counter' and 'event.name' are from different source, with one source emit 'counter'
         * and another source emit 'name', use the following 'if...else if...' to detect the source of event:
         */
        /*
        if (event.counter) {
          this.eventCounter = event.counter;
        } else if (event.name) {
          this.eventName = event.name;
        }
        */

        /**
         * In case both 'counter' and 'name' are from the same source, and there is no need to detect source:
         */
        if (event.isInSellingPage) {
          this.isInSellingPage = event.isInSellingPage;
        }

        if (event.firstname) {
          this.firstname = event.firstname;
        }

        if (event.username) {
          this.username = event.username;
        }
      }
    });
  }

  ngOnDestroy(): void {
    this.destroyed$.next();
    this.destroyed$.complete();
  }

  @ViewChild('menuProfile', { read: MatMenuTrigger, static: false }) menuProfile!: MatMenuTrigger;
  @ViewChild('menuMyEBay', { read: MatMenuTrigger, static: false }) menuMyEBay!: MatMenuTrigger;
  @ViewChild('menuWatchlist', { read: MatMenuTrigger, static: false }) menuWatchlistTrigger!: MatMenuTrigger;

  interval$?: NodeJS.Timeout;

  openMenuProfile() {
    if (this.interval$) {
      clearTimeout(this.interval$);
    }
    this.menuProfile.openMenu();
    /** trick (important!): close all other menus */
    this.menuMyEBay.closeMenu();
    this.menuWatchlistTrigger.closeMenu();
  }

  closeMenuProfile() {
    this.interval$ = setTimeout(() => {
      this.menuProfile?.closeMenu();
    }, 200);
  }

  openMenuWatchlist() {
    if (this.interval$) {
      clearTimeout(this.interval$);
    }
    this.menuWatchlistTrigger.openMenu();
    /** trick (important!): close all other menus */
    this.menuMyEBay.closeMenu();
    this.menuProfile?.closeMenu();
  }

  closeMenuWatchlist() {
    this.interval$ = setTimeout(() => {
      this.menuWatchlistTrigger.closeMenu();
    }, 200);
  }

  openMenuMyEBay() {
    if (this.interval$) {
      clearTimeout(this.interval$);
    }
    this.menuMyEBay.openMenu();
    /** trick (important!): close all other menus */
    this.menuProfile?.closeMenu();
    this.menuWatchlistTrigger.closeMenu();
  }

  closeMenuMyEBay() {
    this.interval$ = setTimeout(() => {
      this.menuMyEBay.closeMenu();
    }, 200);
  }

  logout() {
    /** sign off from localStorage */
    this.jwtHeaderService.signoff();

    /**
     * Remove server side cookie. Since cookie is not in use, this part can be skipped.
     * However, this part is kept for the **learning** purpose.
     */
    this.appService.signoff().pipe(takeUntil(this.destroyed$)).subscribe({
      next: (response) => {
        console.log('AppComponent signoff response:', response);
        console.log('AppComponent signoff response message:', response['message']);

        /**
         * !!! Important !!!
         * (1) Defer 300 miliseconds, so that `this.localStorageService.store('username', response['username'])` can be executed and data saved.
         * (2) Reload page to ensure clean page:
         *       document.location.href = '/#/productlist';
         *       window.location.reload();
         */
        setTimeout(() => {
          // document.location.href = '/#/productlist';
          document.location.reload();
        }, 100);
      },
      error: (error: HttpErrorResponse) => {
        console.error('ProductlistComponent', error);
        this.loading = false;
      }
    });
  }

  displayOverlayForDarkModeTest = false;

  deviceName = '';

  formGroup = new FormGroup({
    deviceName: new FormControl(this.deviceName, Validators.required),
  });

  showDialog(): void {
    this.dialog.open(DarkModeDialogComponent,
      {
        width: '500px'
      });
  }

  gotoSell() {
    this.isInSellingPage = true;
    this.router.navigate(['/sell']);
  }

  gohome() {
    this.isInSellingPage = false;
    this.router.navigate(['/home']);
  }

  toSelling() {
    this.router.navigate(['/sell/selling']);
  }

}
