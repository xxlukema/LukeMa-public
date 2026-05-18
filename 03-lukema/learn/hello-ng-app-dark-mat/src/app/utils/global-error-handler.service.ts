import { HttpErrorResponse } from '@angular/common/http';
import { Injectable, ErrorHandler } from '@angular/core';
import { throwError } from 'rxjs';



@Injectable()
export class GlobalErrorHandlerService implements ErrorHandler {

  constructor() {
  }


  handleError(error: HttpErrorResponse) {
    console.log('=== 4a === GlobalErrorHandlerService', error);
    return throwError(() => '=== 4a === Added in Global Handler' + error.message);
  }

}
