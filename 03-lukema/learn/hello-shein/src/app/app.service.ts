import { NmsService } from '@/app/utils/services/nms.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { env } from 'environments/environment';
import { Observable, catchError } from 'rxjs';


export type MyMap<K extends string | number | symbol, V> = {
  [key in K]: V;
};

@Injectable()
export class AppService {

  constructor(private httpClient: HttpClient,
    private nmsService: NmsService) { }

  private signoutUrl = '/spring/user/signoff';

  signoff(): Observable<Map<string, string>> {
    const url = env.baseUrl + this.signoutUrl;
    return this.httpClient.post<Map<string, string>>(url, null, this.nmsService.httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }

}

