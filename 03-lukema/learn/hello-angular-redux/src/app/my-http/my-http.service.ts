import { CommonService } from '@/app/common/common.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { NmsService } from '../utils/nms.service';
import { env } from '@/environments/environment';


interface UserResponse {
  login: string;
  bio: string;
  company: string;
}

@Injectable({
  providedIn: 'root',
})
export class MyHttpService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly commonService: CommonService,
    private readonly nmsService: NmsService
  ) { }

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

    return this.httpClient.post<any>(postUrl, postDataObject, httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }


  lukeStreamingGetUrl = '/spring/stream';
  public doGetLukeStreaming(): Observable<any> {
    return this.httpClient.post(env.helloBaseUrl + this.lukeStreamingGetUrl, {}, this.nmsService.httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }

  doGetString(): Observable<string> {
    return this.httpClient.get<string>(env.testGetUrl, this.nmsService.httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }

  doGetTyped(): Observable<UserResponse> {
    return this.httpClient.get<UserResponse>(env.testGetUrl, this.nmsService.httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }



}
