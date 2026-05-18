import { Item } from '@/app/sell/draft/draft.service';
import { NmsService } from '@/app/utils/services/nms.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { env } from 'environments/environment';
import { Observable, catchError } from 'rxjs';


/*
export type MyMap<K extends string | number | symbol, V> = {
  [key in K]: V;
};
*/

@Injectable()
export class HomeService {

  constructor(private httpClient: HttpClient,
    private nmsService: NmsService) { }

  private getAllItemsUrl = '/spring/shein/getAllItems';

  getAllItems(): Observable<Item[]> {
    const url = env.baseUrl + this.getAllItemsUrl;

    return this.httpClient.get<Item[]>(url, this.nmsService.httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }

}

