import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment as env } from '../../environments/environment';


@Injectable({
    providedIn: 'root'
})
export class StockChartService {
    constructor(private httpClient: HttpClient) { }

    getQuote(symbol: string): Observable<any> {
        const getQuoteUrl = env.baseUrl + '/getguote/' + symbol;

        /*
        const params = new HttpParams()
            .set('userAccountId', userAccountId ? userAccountId.toString() : null);

        return this.httpClient.get<any>(getQuoteUrl, { params: null })
        */

        console.log('stock-chart', 'getQuoteUrl', getQuoteUrl);

        return this.httpClient.get<any>(getQuoteUrl)
            .pipe(
                catchError(this.handleError)
            );
    }

    private handleError(error: HttpErrorResponse) {
        console.error('stock-chart', 'error', error);
        if (error.error instanceof ErrorEvent) {
            console.error('stock-chart', 'client side error:', error.error.message);
        } else {
            return throwError('Error-111111');
        }
        return throwError('Error-222222');
    }
}
