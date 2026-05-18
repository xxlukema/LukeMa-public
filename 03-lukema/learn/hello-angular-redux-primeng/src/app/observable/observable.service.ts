import { CommonService } from '@/app/common/common.service';
import { env } from '@/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { MyPojoObject } from './observable.component';

@Injectable({
  providedIn: 'root',
})
export class ObservableService {
  constructor(
    private httpClient: HttpClient,
    private commonService: CommonService
  ) {}

  slowpostUrl = env.helloBaseUrl + '/spring/slowpost';

  doSlowPost(myPojoObject: MyPojoObject): Observable<any> {
    return this.httpClient
      .post<any>(this.slowpostUrl, myPojoObject)
      .pipe(catchError(this.commonService.handleError));
  }
}
