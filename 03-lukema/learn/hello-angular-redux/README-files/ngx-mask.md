# `ngx-mask`

[ngx-mask]<https://www.npmjs.com/package/ngx-mask>

## Install

    npm install --save ngx-mask

## Customer Pattern

### 1. `nms.service.ts`

    /** Mask Pattern: activedash\nms-ng-ws\projects\common\src\app\utils\nms.service.ts */
    hostnamePattern = {
      'X': {
        pattern: new RegExp(/[a-zA-Z0-9_\-\\.]/)
      }
    };

    passwordPattern = {
      'A': {
        pattern: new RegExp(/[ -~]/),
        symbol: 'A'
      },
      'X': {
        pattern: new RegExp(/[^ `'"]/),
        symbol: 'X'
      }
    };

### 2. `regran.component.html`

    <!-- activedash\nms-ng-ws\projects\5g\src\app\components\regran\regran.component.html -->
    <input fxFlex type="text" formControlName="username" required [readonly]="submitting" 
              [patterns]="nmsService.hostnamePattern" mask="X{20}"
              [dropSpecialCharacters]="false" (input)="change()" (keyup.enter)="onSubmit()">

### 3. `regran.component.ts`

    /** activedash\nms-ng-ws\projects\5g\src\app\components\regran\regran.component.ts */
    formGroup = new FormGroup({
      name: new FormControl(''),
      port: new FormControl(''),
      comment: new FormControl(''),
      hostname: new FormControl('', Validators.required),
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
