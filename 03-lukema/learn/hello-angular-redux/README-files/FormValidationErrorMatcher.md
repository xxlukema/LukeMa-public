# Form Validation Error State Matcher

Three Levels of Error State Matcher

1. Field Level
2. Component/Template Level
3. Module Level
4. Global App Level

## 1. Field Level

    (1) src\app\form\form.component.html:
      <input type="email" matInput formControlName="email" name="email" [(ngModel)]="email" [errorStateMatcher]="myErrorStateMatcher"
        placeholder="Ex. pat@example.com" [readonly]="submitting" (input)="change()" (keyup.enter)="onSubmit()">
    
    (2) src\app\form\form.component.ts:
    myErrorStateMatcher = new MyErrorStateMatcher();

## 2. Componenet/Template Level

    src\app\form\form.component.ts:
    @Component({
      selector: 'app-form',
      templateUrl: './form.component.html',
      styleUrls: ['./form.component.scss'],
      providers: [
        { provide: ErrorStateMatcher, useClass: MyErrorStateMatcher }
      ]
    })
    export class FormComponent implements OnInit {

## 3. Module Level

    src\app\form\form.module.ts:
    @NgModule({
      providers: [
        { provide: ErrorStateMatcher, useClass: MyErrorStateMatcher }
      ]
    })

## 4. Global App Level

    src\app\app.module.ts:
    @NgModule({
      providers: [
        { provide: ErrorStateMatcher, useClass: MyErrorStateMatcher }
      ]
    })
