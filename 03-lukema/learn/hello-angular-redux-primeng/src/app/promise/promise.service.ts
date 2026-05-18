import { CommonService } from '@/app/common/common.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { env } from '@/environments/environment';
import { Greeting } from './promise.component';

@Injectable({
  providedIn: 'root',
})
export class PromiseService {
  constructor(
    private httpClient: HttpClient,
    private commonService: CommonService
  ) {}

  slowgetUrl = env.helloBaseUrl + '/spring/slowget';

  public doSlowGet(): Observable<any> {
    return this.httpClient
      .get<Greeting>(this.slowgetUrl)
      .pipe(catchError(this.commonService.handleError));
  }
}
