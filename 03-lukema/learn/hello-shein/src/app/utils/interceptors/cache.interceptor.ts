import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Provider } from '@angular/core';
import { Observable } from 'rxjs';

export class CacheInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Add Cache-Control and Pragma headers to prevent IE caching responses
    const clonedRequest = request.clone({
      headers: request.headers
        .append('Cache-Control', 'no-cache')
        .append('Pragma', 'no-cache')
        .append('EXPIRES', '-1')
    });
    return next.handle(clonedRequest);
  }
}


/** Provider for the Interceptor. */
export const cacheInterceptorProvider: Provider = { provide: HTTP_INTERCEPTORS, useClass: CacheInterceptor, multi: true };
