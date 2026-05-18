import { HTTP_INTERCEPTORS, HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable, Provider } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, catchError, of, throwError } from 'rxjs';


@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

  constructor(public router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    console.debug('HttpErrorInterceptor called');

    return next.handle(req).pipe(
      catchError((error) => {

        console.log('=== 1a === error in intercept', error);

        let handled = false;
        let errMsg = '';

        if (error instanceof HttpErrorResponse) {

          console.log('=== 1b === HttpErrorResponse error in intercept', error);

          if (error.error instanceof ErrorEvent) {
            /**
             * Client Side Error:
             */
            console.error('=== 1c === Client Side - Error Event');

            errMsg = `"=== 1c === Client Side - Error: ${error.error.message}`;
          } else {
            /**
             * Server Side Error: Wrong URL, bad header, etc.
             */
            console.log(`=== 1d === Server Side - error status : ${error.status} ${error.statusText}`);

            errMsg = `=== 1d === Server Side - Error Code: ${error.status},  Message: ${error.message}`;

            switch (error.status) {
              case 401:      //login
                // this.router.navigateByUrl("/login");
                console.log('=== 1e === redirect to login 111111');
                handled = true;
                break;
              case 403:     //forbidden
                // this.router.navigateByUrl("/login");
                console.log('=== 1f === redirect to login 222222');
                handled = true;
                break;
            }
          }
        } else {
          console.error('=== 1g === Other Errors', error);
        }

        if (handled) {
          console.log('=== 1h === return back ', error);
          return of(error);
        } else {
          console.log('=== 1i === throw error back to to the subscriber', error);
          return throwError(() => error);
        }

      })
    );
  }
}

/** Provider for the Interceptor. */
export const httpErrorInterceptorProvider: Provider = { provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true };
