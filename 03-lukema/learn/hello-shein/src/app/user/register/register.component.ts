import { BlockUiModule } from '@/app/utils/blockui/blockui.module';
import { BlockUiService } from '@/app/utils/blockui/blockui.service';
import { CapsLockDirective } from '@/app/utils/directives/caps-lock.directive';
import { NmsService } from '@/app/utils/services/nms.service';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { Subject, takeUntil } from 'rxjs';
import { Country, RegisterService, User } from './register.service';
import { LoadingModule } from '@/app/utils/loading/loading.module';

export enum AccountType {
  Personal,
  Business
}

@Component({
  selector: 'app-signin',
  standalone: true,
  imports: [CommonModule, FlexLayoutModule, MatInputModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    CapsLockDirective,
    MatSelectModule,
    MatFormFieldModule,
    LoadingModule,
    BlockUiModule
  ],
  providers: [
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent implements OnInit, OnDestroy {

  constructor(private registerService: RegisterService,
    private blockUiService: BlockUiService,
    public nmsService: NmsService,) { }

  private readonly destroyed$ = new Subject<void>();

  loading = false;

  isPhoneValid(): boolean {
    let ph: string | null | undefined = null;
    if (this.selectedVal === AccountType.Personal) {
      ph = this.formGroupPersonal.get('phone')?.value;
    } else {
      ph = this.formGroupBusiness.get('phone')?.value;
    }

    if (ph) {
      // ph = ph.replaceAll('[-_]', '');
      ph = ph.replaceAll(/[^0-9]/g, '');

      if (ph.length > 3) {
        ph = ph.substring(0, 3) + '-' + ph.substring(3);
        if (ph.length > 7) {
          ph = ph.substring(0, 7) + '-' + ph.substring(7);
        }
      }

      if (this.selectedVal === AccountType.Personal) {
        this.formGroupPersonal.get('phone')?.setValue(ph);
      } else {
        this.formGroupBusiness.get('phone')?.setValue(ph);
      }

      return ph.length === 12;
    }

    return false;
  }

  placeholder = '___-___-____';

  /**
   * Trick: How to make enum avialble to template
   */
  get accountType(): typeof AccountType {
    return AccountType;
  }

  get accountTypeString(): string {
    if (this.selectedVal === AccountType.Personal) {
      return 'Personal';
    } else {
      return 'Business';
    }
  }

  capsOn: Boolean = false;

  showLearnMore = false;

  submitting = false;
  saved = false;

  selectedVal = AccountType.Personal;
  // selectedVal = AccountType.Business;

  firstname = '';
  lastname = '';
  email = '';
  phone = '';
  password = '';
  businessname = '';

  countries?: Country[];
  countryCode: string | null | undefined = '';

  errMsg = '';
  info = '';

  isBuyOnly: boolean | null | undefined = true;

  formGroupPersonal = new FormGroup({
    firstname: new FormControl(this.firstname, [Validators.required]),
    lastname: new FormControl(this.lastname, [Validators.required]),
    // Validators.email takes aa@localhost as valid, while Validators.pattern(this.nmsService.emailPattern) does not.
    email: new FormControl(this.email, [Validators.required, Validators.pattern(this.nmsService.emailPattern)]),
    phone: new FormControl(this.phone, [Validators.required, Validators.minLength(12), Validators.maxLength(12)]),
    password: new FormControl(this.password, [Validators.required]),
  });

  formGroupBusiness = new FormGroup({
    businessname: new FormControl(this.businessname, [Validators.required]),
    // Validators.email takes aa@localhost as valid, while Validators.pattern(this.nmsService.emailPattern) does not.
    email: new FormControl(this.email, [Validators.required, Validators.pattern(this.nmsService.emailPattern)]),
    phone: new FormControl(this.phone, [Validators.required, Validators.minLength(10), Validators.maxLength(10)]),
    password: new FormControl(this.password, [Validators.required]),
    countryCode: new FormControl(this.countryCode, [Validators.required, Validators.minLength(2), Validators.maxLength(2)]),
    isBuyOnly: new FormControl(this.isBuyOnly, []),
  });

  changeCountryCode() {
    this.saved = false;
    console.log('===================== form', this.formGroupBusiness);
    if (this.selectedVal === AccountType.Business && this.formGroupBusiness.get('countryCode')) {
      this.countryCode = this.formGroupBusiness.get('countryCode')?.value;
    }
  }

  onValChange(val: AccountType) {
    this.selectedVal = val;

    if (this.selectedVal === AccountType.Business) {
      if (!this.countries) {
        this.loading = true;

        this.registerService.getAllCountries().pipe(takeUntil(this.destroyed$)).subscribe({
          next: (data) => {
            console.log('RegisterComponent', data);
            this.countries = data;
            this.loading = false;
          },
          error: (error: HttpErrorResponse) => {
            console.error('RegisterComponent', error);
            this.loading = false;
          }
        });
      }
    }
  }

  onKeyUp() {
    /*
    if (this.selectedVal === AccountType.Personal) {
      // console.debug('--- keyup', this.formGroupPersonal);
    } else {
      // console.debug('--- keyup', this.formGroupBusiness);
    }
    */

    this.errMsg = '';
    this.info = '';
  }

  ngOnInit() {
    console.log('RegisterComponent ngOnInit() called.');
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('RegisterComponent ngOnDestroy() called.');

    this.destroyed$.next();
    this.destroyed$.complete();
  }

  onSubmit() {
    if (this.selectedVal === AccountType.Personal) {
      if (!this.formGroupPersonal.valid) {
        return;
      }
    } else {
      if (!this.formGroupBusiness.valid) {
        return;
      }
    }

    let user: User;

    if (this.selectedVal === AccountType.Personal) {
      const firstname = this.formGroupPersonal.get('firstname')?.value?.trim();
      const lastname = this.formGroupPersonal.get('lastname')?.value?.trim();
      const email = this.formGroupPersonal.get('email')?.value?.trim();
      const phone = this.formGroupPersonal.get('phone')?.value;
      const password = this.formGroupPersonal.get('password')?.value?.trim();

      user = {
        firstname: firstname,
        lastname: lastname,
        email: email,
        username: email,
        phone: phone,
        password: password
      };
    } else {
      const businessname = this.formGroupBusiness.get('businessname')?.value?.trim();
      const email = this.formGroupBusiness.get('email')?.value?.trim();
      const phone = this.formGroupBusiness.get('phone')?.value;
      const password = this.formGroupBusiness.get('password')?.value?.trim();
      const countryCode = this.formGroupBusiness.get('countryCode')?.value;
      const isBuyOnly = this.formGroupBusiness.get('isBuyOnly')?.value;

      user = {
        businessname: businessname,
        email: email,
        username: email,
        phone: phone,
        password: password,
        countryCode: countryCode,
        isBuyOnly: isBuyOnly
      };
    }

    this.loading = true;

    this.blockUiService.block();

    this.info = '';
    this.errMsg = '';

    this.registerService.register(user).pipe(takeUntil(this.destroyed$)).subscribe({
      next: (response) => {

        console.debug('-------------- register', response);

        this.submitting = false;
        this.blockUiService.unblock();
        this.saved = true;
        this.info = 'User register success';
        this.loading = false;
      },
      error: (error: HttpErrorResponse) => {
        console.error('RegisterComponent HttpErrorResponse', error);
        this.submitting = false;
        this.saved = false;
        this.blockUiService.unblock();

        this.errMsg = error.error.message;
        this.loading = false;
      },
    });
  }

}
