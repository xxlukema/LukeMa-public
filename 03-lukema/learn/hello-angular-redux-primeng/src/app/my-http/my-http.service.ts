import { CommonService } from '@/app/common/common.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MyHttpService {
  constructor(
    private httpClient: HttpClient,
    private commonService: CommonService
  ) {}

  public doPost(postUrl: string, postDataObject: any): Observable<any> {
    const headers = new HttpHeaders()
      .set('event', 'addAuthCodeClick')
      .set('surveyId', '');
    const params = new HttpParams()
      .set('deadline', 'deadline value')
      .set('surveyInstanceId', 'surveyInstanceId value');
    const httpOptions = {
      headers: headers,
      params: params,
    };

    console.log('my-http.service', 'headers', headers, 'options', httpOptions);

    return this.httpClient
      .post<any>(postUrl, postDataObject, httpOptions)
      .pipe(catchError(this.commonService.handleError));
  }
}
