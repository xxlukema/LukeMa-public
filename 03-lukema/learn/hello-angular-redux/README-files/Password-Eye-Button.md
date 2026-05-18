# Password Eye (See) Button

## Template

          <!-- activedash\nms-ng-ws\projects\5g\src\app\components\regran\regran.component.html -->
          <div fxLayout fxLayoutAlign="center center">
            <span fxFlex="35" class="label required">
              Password
            </span>
            <div fxFlex fxLayout="column" fxLayoutAlign="start end" fxLayoutGap="-1.77em" style="margin-bottom: 0.45em;">
              <input fxFlex type="password" autocomplete="false" formControlName="password" required #password [readonly]="submitting"
                [patterns]="nmsService.passwordPattern" mask="X{20}" style="width: 100%;" (input)="change()" (keyup.enter)="onSubmit()"
                [dropSpecialCharacters]="false">
              <i fxFlex="none" (click)="password.type=(password.type==='password')?'text':'password'" class="eye fa-solid"
                [class]="(password.type=='password')?'fa-eye':'fa-eye-slash'"></i>
            </div>
          </div>

## Style

    /** activedash\nms-ng-ws\projects\5g\src\styles.scss */
    .eye {
      height: fit-content;
      width: 2.2em;
      background-color: cyan;
      padding: 0.5em;
      margin: 0;
      z-index: 100;
    }

## Component

    /** activedash\nms-ng-ws\projects\5g\src\app\components\regran\regran.component.ts */
    formGroup = new FormGroup({
      password: new FormControl('', Validators.required),
    });
  
    /** Mask Pattern: activedash\nms-ng-ws\projects\common\src\app\utils\nms.service.ts */
    hostnamePattern = { 'X': { pattern: new RegExp(/[a-zA-Z0-9_\-\\.]/) } };
    passwordPattern = { 'A': { pattern: new RegExp(/[ -~]/), symbol: 'A' }, 'X': { pattern: new RegExp(/[^ `'"]/), symbol: 'X' } };
