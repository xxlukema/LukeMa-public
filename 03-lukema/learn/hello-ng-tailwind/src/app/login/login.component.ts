import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-login',
  imports: [
    CommonModule,
    MatRadioModule,
    MatSelectModule,
    MatCardModule,
    MatButtonModule,
    MatInputModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(
    public router: Router
  ) { }

  creditialLoginSub$!: Subscription;

  error = '';
  loading = false;

  onSubmit() {
    console.log('LoginComponent', 'onSubmit entered');

    // stop here if form is invalid
    if (this.formGroup.invalid) {
      console.log('LoginComponent', 'loginForm: ' + this.formGroup.invalid);
      return;
    }

    this.loading = true;

    if (this.formGroup.value.password === 'test') {
      sessionStorage.setItem('isLoggedIn', 'true');
      this.router.navigate(['/home']);
      this.loading = false;
    } else {
      this.error = 'Please enter your password';
      this.loading = false;
    }

    /**
    this.creditialLoginSub$ = this.loginService.credentialLogin(this.f.username.value)
      .pipe(first())
      .subscribe(
        (data: Profile) => {
          this.sessionStorageService.store('userAccountId', data.userAccountId);
          this.router.navigate(['/surveys']);
          this.loading = false;
        },
        error => {
          this.error = error;
          this.loading = false;
          console.log('LoginComponent', this.error);

          this.showErrorMsg = true;
        }
      );
      */
  }

  /**
   * Fields to hold data to construct request body or params
   */
  username = 'lukemal@yopmail.com';
  password = 'test';

  /**
   * Form related fields
   */
  submitting = false;
  saved = false;
  errMsg = '';
  info = '';

  /**
   * formGroup is for form validation. It is not convenient to extract form control values from fromGroup.
   */
  formGroup = new FormGroup({
    username: new FormControl(this.username, [Validators.required, Validators.minLength(3), Validators.maxLength(20)]),
    password: new FormControl(this.password, [Validators.required, Validators.minLength(3), Validators.maxLength(20)]),
  });

  onReset() {
    console.log('LoginComponent', 'onReset entered');
    this.submitting = false;
  }

  change() {
    this.saved = false;
    this.errMsg = '';
  }

  selectionChange() {
    this.change();
  }

  get f() {
    return this.formGroup.controls;
  }

}
