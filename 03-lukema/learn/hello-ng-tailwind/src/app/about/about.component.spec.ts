import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutComponent } from './about.component';
import { provideRouter } from '@angular/router';
import { authGuard } from '../utils/guard/auth.guard';

describe('AboutComponent', () => {
  let component: AboutComponent;
  let fixture: ComponentFixture<AboutComponent>;

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
      imports: [AboutComponent],
      providers: [
        /**
         * provideRouter([]) - Fix of `No provider for ActivatedRoute!`
         */
        provideRouter([]),
        { provide: authGuard, useValue: mockAuthGuard }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AboutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
