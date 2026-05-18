import { env } from '@/environments/environment';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { DateUpdated } from './date-updated.rest';
import { House } from './house.rest';

@Injectable({
    providedIn: 'root'
})
export class PropertyReportService {
    constructor(private httpClient: HttpClient) { }

    getDateUpdatedUrl = env.apiEndpoint + '/rest/house/getDateUpdated';
    getPropertyListUrl = env.apiEndpoint + '/rest/house/getPropertyList';

    getDateUpdated(): Observable<DateUpdated> {
        return this.httpClient.get<DateUpdated>(this.getDateUpdatedUrl)
            .pipe(
                catchError(this.handleError)
            );
    }

    getPropertyList(): Observable<House[]> {
        return this.httpClient.get<House[]>(this.getPropertyListUrl)
            .pipe(
                catchError(this.handleError)
            );
    }

    private handleError(error: HttpErrorResponse) {
        console.error('property-report', 'error', error);
        if (error.error instanceof ErrorEvent) {
            console.error('client side error:', error.error.message);
        } else {
            return throwError('Error-111111');
        }
        return throwError('Error-222222');
    }
}
