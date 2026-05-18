import { FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';


/**
 * Error when invalid control is (1) dirty, (2) touched, or (3) submitted.
 */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null): boolean {

    const isSubmitted = form ? form.submitted : false;
    const invalid = control ? control.invalid : false;
    const dirty = control ? control.dirty : false;
    const touched = control ? control.touched : false;

    return (invalid && (dirty || touched || isSubmitted));

    // return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}
