import { HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Buffer } from 'buffer';
import { Observable, Subject, throwError } from 'rxjs';

export interface NmsError {
  code: number,
  message: string
}

export interface NmsState {
  canCreateTicket?: boolean
}

@Injectable({
  providedIn: 'root'
})
export class NmsService {

  constructor(
    // private readonly keycloakService: KeycloakService,
    private router: Router
  ) { }

  public userkey = 'new---Date.now()';

  public logout() {
    // this.keycloakService.clearToken();
    // this.keycloakService.logout(location.origin + '/#/login');
  }

  public httpOptions = {
    /**
     * Comment out 'Cache-Control', 'Pragma', 'EXPIRES' because 'https://api.github.com/users/seeschweiler' does not accept these headers.
     */
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Cache-Control': 'no-cache',
      'Pragma': 'no-cache',
      'EXPIRES': '-1'
    })
  };

  handleError = (error: HttpErrorResponse) => {
    console.error('=== 2 === NmsService handleError HttpClient', 'error', error);
    let status = 0;
    let msg = 'test error';

    if (error.status) {
      if (error.status === 0) {
        status = this.errors.failToConnect.code;
      } else {
        status = error.status;
      }
    } else {
      status = this.errors.unknown.code;
    }
    if (error.message) {
      msg = error.message;
    } else if (error.error) {
      if (typeof error.error == 'string') {
        msg = error.error;
      } else if (error.error.message) {
        msg = error.error.message;
      }
    }
    if (msg.length === 0) {
      msg = this.errors.failToConnect.message;
    }

    if (error.error instanceof ErrorEvent) {
      /**
       * Client Side Error
       */
      console.error('HttpClient ErrorEvent', 'client side error:', error.error.message);
    } else {
      /**
       * Server Side Error
       */
    }

    try {
      this.toError(status, msg);
    } finally {
      // eslint-disable-next-line no-unsafe-finally
      return throwError(() => 'Error-111111: ' + error);
    }
  };

  toBase64(str: string): string {
    if (str) {
      return Buffer.from(str).toString('base64');
    }
    return '';
  }

  fromBase64(str: string): string {
    if (str) {
      return Buffer.from(str, 'base64').toString();
    }
    return '';
  }

  hostnamePattern = { 'X': { pattern: new RegExp(/[a-zA-Z0-9_\-\\.]/) } };

  passwordPattern = { 'A': { pattern: new RegExp(/[ -~]/), symbol: 'A' }, 'X': { pattern: new RegExp(/[^ `'"]/), symbol: 'X' } };

  errorUrl = '/home/error/error';

  toNmsError(error: NmsError) {
    this.router.navigate([this.errorUrl], {
      queryParams:
      {
        code: error.code,
        message: error.message
      }
    });
  }

  toError(code: number, message: string) {
    this.router.navigate([this.errorUrl], {
      queryParams:
      {
        code: code,
        message: message
      }
    });
  }

  errors = {
    // client errors
    e400: { code: 400, message: 'Bad Request' },
    e403: { code: 403, message: 'Forbidden' },
    e404: { code: 404, message: 'Not Found' },
    e405: { code: 405, message: 'Method Not Allowed' },
    e408: { code: 408, message: 'Request Timeout' },
    // server errors
    e500: { code: 500, message: 'Internal Server Error' },
    e503: { code: 503, message: 'Service Unavailable' },
    // nms errors
    failToConnect: { code: 520, message: 'Unable To Connect To Server' },
    unknown: { code: 555, message: 'Unknown Error' },
    connect: { code: 556, message: null },
  };

  initPageSize(pageSize: string): number {
    const size = localStorage.getItem(pageSize);
    if (size) {
      return parseInt(size);
    } else {
      return 20;
    }
  }

  getItemAsNumberWithDefault(item: string, val: number): number {
    const value = localStorage.getItem(item);
    if (value) {
      return parseInt(value);
    } else {
      return val;
    }
  }

  nmsState: Observable<NmsState> = new Observable<NmsState>();
  nmsStateSubject = new Subject<boolean>();

  compareId = (o1: any, o2: any): boolean => {
    return o1.id == o2.id;
  };

  compareObjects = (o1: any, o2: any): boolean => {
    return o1 == o2;
  };

}
