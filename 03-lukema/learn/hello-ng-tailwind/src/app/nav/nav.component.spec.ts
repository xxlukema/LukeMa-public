import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavComponent } from './nav.component';
import { provideRouter } from '@angular/router';
import { authGuard } from '../utils/guard/auth.guard';

describe('NavComponent', () => {
  let component: NavComponent;
  let fixture: ComponentFixture<NavComponent>;

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
      imports: [NavComponent],
      providers: [
        /**
         * provideRouter([]) - Fix of `No provider for ActivatedRoute!`
         */
        provideRouter([]),
        { provide: authGuard, useValue: mockAuthGuard }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(NavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
