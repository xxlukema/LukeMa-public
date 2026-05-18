import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class CacheInterceptor implements HttpInterceptor {

  constructor() { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
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
