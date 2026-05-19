import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';

export const bookGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (!authService.getToken()) {
    authService.loadToken();
  }

  if (authService.isAdmin()) {
    return true;
  } else {
    router.navigate(['app-forbidden']);
    return false;
  }
};
