import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

/**
 *  Remember to provide it so that it can be used:
 *
 *  providers: [{
 *    provide: HTTP_INTERCEPTORS,
 *    useClass: AppInterceptor,
 *    multi: true
 *  }],
 */
@Injectable()
export class AppInterceptor implements HttpInterceptor {
    intercept(httpRequest: HttpRequest<any>, nextHttpHandler: HttpHandler): Observable<HttpEvent<any>> {

        console.log('Interceptor invoked. ' + httpRequest.url);

        if (!httpRequest.url.includes('github')) {
            httpRequest.headers.set('Authorization', 'token my token');
        }

        /*
        const interceptedHttpRequest = httpRequest.clone({
            headers: httpRequest.headers.set('Authorization', 'token my token')
            //headers: httpRequest.headers.set('test', 'token my token')
        });
        */

        // return nextHttpHandler.handle(interceptedHttpRequest);
        return nextHttpHandler.handle(httpRequest);
    }
}
