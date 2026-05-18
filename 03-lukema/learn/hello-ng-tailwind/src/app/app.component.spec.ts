import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { authGuard } from './utils/guard/auth.guard';
import { provideRouter } from '@angular/router';

/**
 * ng test --watch=false --include src/app/app.component.spec.ts
 *
 * ng test --watch=false --source-map=false --code-coverage=false
 * ng test --watch=false --source-map=false --code-coverage=false --main src/app/app.component.spec.ts
 * ng test --watch=false --source-map=false --code-coverage=false --main src/app/app.component.spec.ts --tsconfig src/tsconfig.spec.json
 * ng test --watch=false --source-map=false --code-coverage=false --main src/app/app.component.spec.ts --tsconfig src/tsconfig.spec.json --karma-config karma.conf.js
 */
describe('AppComponent', () => {

  let fixture: ComponentFixture<AppComponent>;

  beforeEach(async () => {

    /**
     * Mock security guard
     */
    const mockAuthGuard = {
      canActivate: () => {
        return true; // Mock guard
      }
    };

    await TestBed.configureTestingModule({
      imports: [AppComponent],
      providers: [
        /**
         * provideRouter([]) - Fix of `No provider for ActivatedRoute!`
         */
        provideRouter([]),
        { provide: authGuard, useValue: mockAuthGuard }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
  });

  it('should create the app', () => {
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have the 'hello-ng-tailwind' title`, () => {
    const app = fixture.componentInstance;
    expect(app.title).toEqual('hello-ng-tailwind');
  });

  it('should render title', () => {
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('#header')?.textContent).toContain('header works!');
  });

  it('should add two numbers', () => {
    const app = fixture.componentInstance;
    expect(app.add(1, 2)).toEqual(3);
  });

  it('should subtract num2 by num1', () => {
    const app = fixture.componentInstance;
    app.num1 = 100;
    app.num2 = 201;
    expect(app.subtractNum2ByNum1()).toEqual(101);
  });
});
