import { env } from '@/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of } from 'rxjs';
import { CommonService } from '../common/common.service';

@Injectable()
export class Whidbey2Service {

  constructor(private readonly httpClient: HttpClient,
    private readonly commonService: CommonService) { }


  initUrl = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyBH6IHFDJavzp1yg4LXg-prLTIFq9tkPKA&libraries=visualization';

  siteUrl = '/mms-dash/statusMap';

  initLoad(): Observable<any> {
    return this.httpClient.get<any>(this.initUrl).pipe(
      catchError(this.commonService.handleError)
    );
  }


  getStatus(): Observable<any> {
    /*
    return this.httpClient.get<any>(env.baseUrl + this.siteUrl, this.commonService.httpOptions).pipe(
      catchError(this.commonService.handleError)
    );
    */

    const terms = [
      {
        siteId: 1,
        siteName: 'Air Field',
        health: 'Healthy'
      },
      {
        siteId: 2,
        siteName: 'Refueling',
        health: 'Healthy'
      },
      {
        siteId: 3,
        siteName: '#976',
        health: 'Healthy'
      },
      {
        siteId: 4,
        siteName: 'H15',
        health: 'Healthy'
      },
      {
        siteId: 5,
        siteName: 'H12',
        health: 'Healthy'
      },
      {
        siteId: 6,
        siteName: 'H6',
        health: 'Healthy'
      },
      {
        siteId: 7,
        siteName: 'H6 Outside',
        health: 'Healthy'
      },
    ];

    return of(terms);
  }


}
