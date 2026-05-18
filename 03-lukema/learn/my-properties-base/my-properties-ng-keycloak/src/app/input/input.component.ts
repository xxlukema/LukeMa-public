import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.scss'],
})
export class InputComponent implements OnInit {
  constructor(public router: Router) {}

  /*
    import { FormControl, Validators, FormGroup } from '@angular/forms';

    myFormControl = new FormControl(
        { value: 'My String Value', disabled: true },
        [Validators.required, Validators.maxLength(30)]
    );

    formGroup = new FormGroup({
        name: new FormControl('', [Validators.required]),
        email: new FormControl('', [Validators.required]),
        address: new FormGroup({
            zipCode: new FormControl(''),
            street: new FormControl(''),
            number: new FormControl('')
        })
    });

    formGroup = new FormGroup({
        phones: new FormArray([
            new FormGroup({
                number: new FormControl('', [Validators.required]),
                type: new FormControl('Primary')
            }),
            new FormGroup({
                number: new FormControl(''),
                type: new FormControl('Secondary')
            })
        ])
    });
    */

  inputFormGroup = new FormGroup({
    name: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
  });

  ngOnInit(): void {}

  onSubmit(): void {
    console.log('input', 'inputFormGroup', this.inputFormGroup.value);
  }

  get errorName(): string {
    return 'Error with name';
  }

  get errorEmail(): string {
    return 'Error with email';
  }
}
