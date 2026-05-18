# `JWT Cookie` and `Authentication Bearer` Header Missing

After user logon, spring boot sets `JWT Cookie` and `Authentication Bearer` to caller response. In order to carry these `JWT Cookie` and `Authentication Bearer`
in subsequence server calls, **TWO** things need to be done.

1. Add `withCredentials: true` to `requestOptions`.
2. `Access-Control-Allow-Origin=http://localhost:4200`. Credential is not supported if the CORS header `Access-Control-Allow-Origin=*`.
3. (skip. not true) After get login success response, use `document.location.href = '/#/register';` or `window.location.reload();`. Do not use `this.router.navigate(['/register']);`.
4. Before navigate to home, defer 300 miliseconds to allow username saved into LocalStorage

## To attach jwt cookie and auth headers

### 1. use `{ withCredentials: true }` in `requestOptions`

    http.post('/auth/login', user, { withCredentials: true });
    http.get('/test/user', { withCredentials: true });
    http.get('/test/mod', { withCredentials: true });
    http.get('/test/admin', { withCredentials: true });

### 2. use `HttpInterceptor`

    # auth-interceptor.ts
    import { Injectable } from '@angular/core';
    import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';
    import { Observable } from 'rxjs';
    
    @Injectable()
    export class AuthInterceptor implements HttpInterceptor {
      intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        req = req.clone({
          withCredentials: true,
        });
    
        return next.handle(req);
      }
    }
    
    export const authInterceptorProviders = [
      { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    ];

## Signin Code

    this.channel$ = this.signinService.signin(user).subscribe({
      next: (response: HttpResponse<SigninUser>) => {

        this.localStorageService.store('username', response['username']);

        this.blockUiService.unblock();
        this.loading = false;

        /**
         * !!! Important !!!
         * Defer 300 miliseconds, so that `this.localStorageService.store('username', response['username'])` can be executed and data saved.
         */
        setTimeout(() => {
          // this.router.navigate(['/home']);
          // this.router.navigate(['/productlist']);
          this.router.navigate(['/register']);

          /**
           * Do not have to refresh page.
           */
          // document.location.href = '/#/register';
          // window.location.reload();
        }, 300);
      },
      error: (error: HttpErrorResponse) => {
        console.error('RegisterComponent HttpErrorResponse', error);
        this.blockUiService.unblock();

        this.localStorageService.clear('username');

        this.errMsg = error.error.message;
        this.loading = false;
      },
    });
