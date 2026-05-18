import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { InputRoutingModule } from './input-routing.module';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.scss'],
  imports: [
    CommonModule,
    InputRoutingModule,
    ReactiveFormsModule,
    MatInputModule,
    MatFormFieldModule
  ],
})
export class InputComponent implements OnInit {
  constructor(public router: Router) { }

  /**
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

  ngOnInit(): void {
    console.log('InputComponent initialized');
  }

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
