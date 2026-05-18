# ng test

    ng generate config karma

    ng test
    ng test --code-coverage
    ng test --include path/to/your/test.spec.ts

    ng test --include src/app/app.component.spec.ts

## `describe()` vs `it()` vs `fdescribe` (focused)

- `describe()`: Test suite
- `fdescribe()`: Test suite
- `xdescribe()`: Test suite
- `it()`: A single test
- `fit()`: A single test
- `xit()`: A single test

## `xdescribe()` and `xit()`

## Skip securities

### 1. Mock security service

    import { TestBed } from '@angular/core/testing';
    import { YourComponent } from './your.component';
    import { YourSecurityService } from './your-security.service';
    import { of } from 'rxjs';
    
    describe('YourComponent', () => {
      beforeEach(() => {
        const mockSecurityService = {
          isAuthenticated: () => of(true), // Mock authentication
          // ... other security methods
        };
    
        TestBed.configureTestingModule({
          declarations: [YourComponent],
          providers: [
            { provide: YourSecurityService, useValue: mockSecurityService }
          ]
        }).compileComponents();
      });
    });

### 2. Use env variable to skip security on tests

    # In `src\app\utils\guard\auth.guard.ts`:

## `*ngFor` vs `@for` `@empty`

    <ul>
      @for (item of items; track item.id) {
        <li>{{ item.name }}</li>
      } @empty {
        <li>No items available.</li>
      }
    </ul>

## `*ngIf` vs `@if`

## Test

1. Arrange
2. Act
3. Assert
