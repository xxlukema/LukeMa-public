import { JwtHeaderService } from '@/app/utils/services/jwt-header.service';
import { HTTP_INTERCEPTORS, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable, Provider } from '@angular/core';
import { Observable, last, tap } from 'rxjs';


/**
 * **********************************************************************
 * * To enable, uncomment the following line in `src/app/app.module.ts` *
 * **********************************************************************
 *
 * // jwtInterceptorProvider,
 * // or
 * // { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
 */
@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(
    private jwtHeaderService: JwtHeaderService
  ) { }

  /**
   * Faulty: jwtToken might change momentarily. Autowire the value at instanciation time cannot capture the new jwtToken.
   */
  // @SessionStorage('jwtToken', '') jwtToken?: string;

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add auth header with jwt if account is logged in and request is to the api url

    /**
     * Retrieve new jwtToken for every intercetion.
     */
    const jwtTokenEncoded = this.jwtHeaderService.jwtTokenEncoded;

    console.debug('---- JwtInterceptor called. jwtTokenEncoded:', jwtTokenEncoded);

    if (jwtTokenEncoded) {
      request = request.clone({
        withCredentials: true,
        setHeaders: { Authorization: `Bearer ${jwtTokenEncoded}` }
      });
    } else {
      request = request.clone({
        withCredentials: true,
      });
    }

    return next.handle(request).pipe(
      last(),
      tap({
        next: (httpEvent: HttpEvent<any>) => {
          // Skip request
          if (httpEvent.type === 0) {
            return;
          }
          console.debug('----- JwtInterceptor response:', httpEvent);

          if (httpEvent instanceof HttpResponse) {
            const jwtToken = httpEvent.headers.get('Authorization');
            if (jwtToken) {
              this.jwtHeaderService.storeToken(jwtToken);
            }
          }
        }
      })
    );
  }
}

/** Provider for the Interceptor. */
export const jwtInterceptorProvider: Provider = { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true };
