import { CommonService } from '@/app/common/common.service';
import { env } from '@/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ELEMENT_DATA_ARRAY } from './my-mat-table-data-source';

@Injectable()
export class MyMatTableService {

  constructor(private httpClient: HttpClient,
    private commonService: CommonService) { }

  paramUrl = '/reports';

  findLessons(courseId: number, filter: string, sortDirection: string, pageIndex: number, pageSize: number) {
    throw new Error('Method not implemented.');
  }

  getTableData(): Observable<any> {

    const userHttpData = false;

    if (userHttpData) {
      return this.httpClient.get<any>(env.baseUrl + this.paramUrl, this.commonService.httpOptions).pipe(
        catchError(this.commonService.handleError)
      );
    } else {
      return of(ELEMENT_DATA_ARRAY);
    }
  }

}
