import { CategoryConditions } from '@/app/item/item.service';
import { NmsService } from '@/app/utils/services/nms.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { env } from 'environments/environment';
import { Observable, catchError } from 'rxjs';


@Injectable()
export class FindMatchService {

  constructor(private httpClient: HttpClient,
    private nmsService: NmsService) { }

  private getConditionsByTitleUrl = '/spring/shein/getConditionsByTitle/{title}';
  private getAllCategoriesUrl = '/spring/shein/getAllCategories';

  getCategoryConditions(title: string): Observable<CategoryConditions> {
    const base64Title = this.nmsService.toBase64Urlsafe(title);
    const url = env.baseUrl + this.getConditionsByTitleUrl.replace('{title}', base64Title);

    return this.httpClient.get<CategoryConditions>(url, this.nmsService.httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }

  getAllCategories(): Observable<CategoryConditions[]> {
    const url = env.baseUrl + this.getAllCategoriesUrl;

    return this.httpClient.get<CategoryConditions[]>(url, this.nmsService.httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }

}
