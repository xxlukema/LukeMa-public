import { BlockUiModule } from '@/app/utils/blockui/blockui.module';
import { BlockUiService } from '@/app/utils/blockui/blockui.service';
import { CapsLockDirective } from '@/app/utils/directives/caps-lock.directive';
import { EventService } from '@/app/utils/services/event.service';
import { JwtHeaderService } from '@/app/utils/services/jwt-header.service';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { Subject, takeUntil } from 'rxjs';
import { User } from '../register/register.service';
import { SigninService, SigninUser } from './signin.service';


@Component({
  selector: 'app-signin',
  standalone: true,
  imports: [CommonModule, FlexLayoutModule, MatInputModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatFormFieldModule,
    CapsLockDirective,
    BlockUiModule,
  ],
  templateUrl: './signin.component.html',
  styleUrl: './signin.component.scss'
})
export class SigninComponent implements OnInit, OnDestroy {

  constructor(
    private blockUiService: BlockUiService,
    private signinService: SigninService,
    private eventService: EventService,
    private jwtHeaderService: JwtHeaderService,
    private router: Router) { }

  private readonly destroyed$ = new Subject<void>();

  loading = false;

  username?: string | null = 'luke.ma.2023@gmail.com';
  firstname?: string | null;

  capsOn: Boolean = false;

  ngOnInit(): void {
    this.username = localStorage.getItem('username');
    this.firstname = localStorage.getItem('firstname');
    this.staySignedIn = localStorage.getItem('staySignedIn') == null ? false : (localStorage.getItem('staySignedIn') === 'true');
  }


  staySignedIn?: boolean;

  showLearnMore = false;

  password = 'test.1234';
  errMsg = '';

  formGroup = new FormGroup({
    // Validators.email takes aa@localhost as valid, while Validators.pattern(this.nmsService.emailPattern) does not.
    // username: new FormControl(this.username, [Validators.required, Validators.pattern(this.nmsService.emailPattern)]),
    // username: new FormControl(this.username, [Validators.required, Validators.email]),
    username: new FormControl(this.username, [Validators.required, Validators.minLength(5)]),
    password: new FormControl(this.password, [Validators.required]),
    staySignedIn: new FormControl(this.staySignedIn, []),
  });

  usernameErr = '';

  onKeyUp() {
    this.errMsg = '';
  }

  onKeyUpUsername() {
    this.errMsg = '';
  }

  onChange() {
    if (this.formGroup.get('staySignedIn') && this.formGroup.get('staySignedIn')!.value === true) {
      this.staySignedIn = true;
    } else {
      this.staySignedIn = false;
    }

    localStorage.setItem('staySignedIn', String(this.staySignedIn));
  }

  ngOnDestroy(): void {
    console.log('SigninComponent ngOnDestroy() called.');
    /**
     * Unsbuscribe from Observable channels here.
     */
    this.destroyed$.next();
    this.destroyed$.complete();
  }

  switchUser() {
    this.jwtHeaderService.signoff();
    this.username = null;
    this.firstname = null;
    document.location.reload();
  }

  onSubmit() {
    if (!this.formGroup.valid) {
      return;
    }

    this.blockUiService.block();

    this.loading = true;

    this.errMsg = '';

    const username = this.formGroup.get('username')?.value?.trim();

    const user: SigninUser = {
      username: username,
      password: this.formGroup.get('password')?.value?.trim(),
    };

    this.signinService.signin(user).pipe(takeUntil(this.destroyed$)).subscribe({
      next: (response: User) => {

        console.debug('-------------- login response', response, response.username);

        /**
         * save `username` and `firstname` data to `localStorage`
         */
        this.username = response.username;
        if (this.username) {
          localStorage.setItem('username', this.username);
        }
        this.firstname = response.firstname;
        if (this.firstname) {
          localStorage.setItem('firstname', this.firstname);
        }

        /**
         * It is faster to communicate between components using **injected service**. However, communicating
         * using EventService is selected, purely for **learning** purpose.
         */
        this.eventService.emitEvent({
          username: this.username,
          firstname: this.firstname,
        });

        this.blockUiService.unblock();
        this.loading = false;

        /**
         * !!! Important !!!
         * (1) Defer 300 miliseconds, so that `this.localStorageService.store('username', response['username'])` can be executed and data saved.
         * (2) Reload page to ensure clean page:
         *       document.location.href = '/#/productlist';
         *       window.location.reload();
         */
        setTimeout(() => {
          // document.location.href = '/#/home';
          // document.location.href = '/#/register';
          // document.location.href = '/#/productlist';
          // window.location.reload();
          this.router.navigate(['home']);
        }, 80);
      },
      error: (error: HttpErrorResponse) => {
        console.error('RegisterComponent HttpErrorResponse', error);
        this.blockUiService.unblock();

        localStorage.removeItem('username');
        this.username = '';

        this.errMsg = error.error.message;
        this.loading = false;
      },
    });
  }

}
