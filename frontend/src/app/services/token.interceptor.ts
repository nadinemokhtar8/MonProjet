import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';

const exclude_array: string[] = ['/login', '/register', '/verifyEmail'];

function toExclude(url: string) {
  let found = false;
  exclude_array.forEach((e) => {
    if (url.search(e) !== -1) {
      found = true;
    }
  });
  return found;
}

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const jwt = authService.getToken();

  if (!toExclude(req.url) && jwt) {
    const reqWithToken = req.clone({
      setHeaders: { Authorization: 'Bearer ' + jwt }
    });
    return next(reqWithToken);
  }
  return next(req);
};
