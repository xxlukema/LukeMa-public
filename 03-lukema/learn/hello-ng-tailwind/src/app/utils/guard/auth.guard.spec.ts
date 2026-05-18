import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';
import { authGuard } from './auth.guard';

xdescribe('authGuard', () => {

  const executeGuard: CanActivateFn = (...guardParameters) =>
    TestBed.runInInjectionContext(() => authGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [authGuard]
    });
    TestBed.inject(authGuard);
  });

  xit('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });

});
