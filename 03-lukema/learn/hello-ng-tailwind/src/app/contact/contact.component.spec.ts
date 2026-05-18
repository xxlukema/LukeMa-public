import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactComponent } from './contact.component';
import { provideRouter } from '@angular/router';
import { authGuard } from '../utils/guard/auth.guard';

describe('ContactComponent', () => {
  let component: ContactComponent;
  let fixture: ComponentFixture<ContactComponent>;

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
      imports: [ContactComponent],
      providers: [
        /**
         * provideRouter([]) - Fix of `No provider for ActivatedRoute!`
         */
        provideRouter([]),
        { provide: authGuard, useValue: mockAuthGuard }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ContactComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
