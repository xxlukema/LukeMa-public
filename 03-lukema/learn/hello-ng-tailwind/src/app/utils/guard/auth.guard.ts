import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);

  /**
   * Use environment variables to skip security for unit tests
   */
  const isLoggedIn = sessionStorage.getItem('isLoggedIn') === 'true'; // Replace with your authentication logic

  if (isLoggedIn) {
    return true;
  } else {
    router.navigate(['/login']);
    return false;
  }
};
