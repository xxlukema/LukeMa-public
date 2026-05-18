import { HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { SessionStorage } from 'ngx-webstorage';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CommonService {
  constructor(private router: Router) {}

  /**
   * Most likely @SessionStorage('loginName', 'default value') is not working.
   */
  @SessionStorage('loginName', 'default value') loginName?: string;

  public userkey = 'new---Date.now()';

  public logout() {}

  public httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Cache-Control': 'no-cache',
      Pragma: 'no-cache',
      EXPIRES: '-1',
    }),
  };

  handleError = (error: HttpErrorResponse) => {
    console.error('CommonService', 'error', error);
    if (error.error instanceof ErrorEvent) {
      console.error(
        'CommonService ErrorEvent',
        'client side error:',
        error.error.message
      );
    } else {
      return throwError(() => 'Error-111111');
    }
    return throwError(() => 'Error-222222');
  };
}
