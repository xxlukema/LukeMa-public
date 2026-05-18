import { Item } from '@/app/sell/draft/draft.service';
import { NmsService } from '@/app/utils/services/nms.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { env } from 'environments/environment';
import { Observable, catchError } from 'rxjs';

export interface CategoryConditions {
  category: string,
  conditions: string[],
}

@Injectable()
export class ItemService {

  constructor(private httpClient: HttpClient,
    private nmsService: NmsService) { }

  private getItemUrl = '/spring/shein/getItem/{itemId}';

  getItem(id: number): Observable<Item> {
    const url = env.baseUrl + this.getItemUrl.replace('{itemId}', id.toString());

    return this.httpClient.get<Item>(url, this.nmsService.httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }

}
