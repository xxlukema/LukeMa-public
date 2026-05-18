import { MyErrorStateMatcher } from '@/app/utils/my-error-state.matcher';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';


@Component({
  standalone: false,
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss'],
  providers: [
    // { provide: ErrorStateMatcher, useClass: MyErrorStateMatcher }
  ]
})
export class FormComponent implements OnInit {

  constructor(
    public router: Router
  ) { }


  myErrorStateMatcher = new MyErrorStateMatcher();


  /**
   * Fields to hold data to construct request body or params
   */
  email = '';

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
    email: new FormControl(this.email, [Validators.required, Validators.email]),
  });

  cancel() {
    this.router.navigate(['/home/dashboards/auxiliary']);
  }

  change() {
    this.saved = false;
    this.errMsg = '';
  }

  selectionChange() {
    this.change();
  }

  onSubmit() {
    if (!this.formGroup.valid || this.saved || this.submitting || this.errMsg.length > 0) {
      return;
    }

    this.submitting = true;
    this.saved = false;
    this.errMsg = '';
    this.info = '';

    /**
     * build request and submit request using HttpClient
     */
  }

  ngOnInit(): void {
  }

}
