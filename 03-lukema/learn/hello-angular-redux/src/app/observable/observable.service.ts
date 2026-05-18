import { CommonService } from '@/app/common/common.service';
import { env } from '@/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { MyPojoObject } from './observable.component';


export interface NestedPojo {
  id: number;
  name: string;
  ChildPojo: ChildPojo;
}

export interface ChildPojo {
  id: number;
  name: string;
}

@Injectable({
  providedIn: 'root',
})
export class ObservableService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly commonService: CommonService
  ) { }

  slowpostUrl = env.helloSslBaseUrl + '/spring/slowpost';

  nestedUrl = env.helloSslBaseUrl + '/luke/nested';


  doSlowPost(myPojoObject: MyPojoObject): Observable<any> {
    return this.httpClient.post<any>(this.slowpostUrl, myPojoObject).pipe(
      catchError(this.commonService.handleError)
    );
  }

  doNested(): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Basic ' + btoa('user:user')
      }),
      withCredentials: true
    };
    return this.httpClient.get<any>(this.nestedUrl, httpOptions).pipe(
      catchError(this.commonService.handleError)
    );
  }


}
