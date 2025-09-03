import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { CanActivateFn } from '@angular/router';

export const canActivateAuth: CanActivateFn = (route, state) => {
  const isLogIn = inject(AuthService).isAuth;
  if (isLogIn) {
    return true;
  }
  return inject(Router).createUrlTree(['/login']);
};
