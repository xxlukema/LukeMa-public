import { Injectable } from '@angular/core';

import {
    HttpEvent, HttpInterceptor, HttpHandler, HttpRequest,
    HttpResponse, HttpClient, HttpHeaders, HttpErrorResponse
} from '@angular/common/http';

import { Observable } from 'rxjs';

import { tap } from 'rxjs/operators';

@Injectable()
export class MyInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // when there is POST request
        /*
        if (req.method === 'POST') {
            const content_type = 'application/x-www-form-urlencoded';
            const req = req.clone({
                headers: req.headers.set('Content-Type', content_type),
                body: 'my body'
            });

            return next.handle(accessReq);
        }
        */

        const value = req.headers.get('Access-Control-Allow-Headers');

        console.log('From interceptor [Access-Control-Allow-Headers]: ' + value);

        return next.handle(req)
            .pipe(tap(
                event => {
                    if (event instanceof HttpResponse) {

                        console.log('From interceptor: event.headers.keys.length = ' + event.headers.keys.length);

                        console.log('From interceptor headers: ' + JSON.stringify(event.headers));
                    }
                }
            )
            );
    }
}
