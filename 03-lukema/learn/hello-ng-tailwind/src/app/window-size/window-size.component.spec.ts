import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WindowSizeComponent } from './window-size.component';
import { provideRouter } from '@angular/router';
import { authGuard } from '../utils/guard/auth.guard';

describe('WindowSizeComponent', () => {
  let component: WindowSizeComponent;
  let fixture: ComponentFixture<WindowSizeComponent>;

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
      imports: [WindowSizeComponent],
      providers: [
        /**
         * provideRouter([]) - Fix of `No provider for ActivatedRoute!`
         */
        provideRouter([]),
        { provide: authGuard, useValue: mockAuthGuard }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(WindowSizeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
