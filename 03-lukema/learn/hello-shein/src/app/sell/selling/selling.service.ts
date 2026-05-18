import { Item } from '@/app/sell/draft/draft.service';
import { NmsService } from '@/app/utils/services/nms.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { env } from 'environments/environment';
import { Observable, catchError } from 'rxjs';


@Injectable()
export class SellingService {

  constructor(private httpClient: HttpClient,
    private nmsService: NmsService) { }

  private getItemsForSellerUrl = '/spring/shein/getItemsForSeller/{sellerUsername}';
  private listItemUrl = '/spring/shein/listItem';
  private unListItemUrl = '/spring/shein/unListItem';
  private deleteItemUrl = '/spring/shein/deleteItem/{id}';

  getItemsForSeller(sellerUsername: string): Observable<Item[]> {
    const username = this.nmsService.toBase64Urlsafe(sellerUsername);
    const url = env.baseUrl + this.getItemsForSellerUrl.replace('{sellerUsername}', username);

    return this.httpClient.get<Item[]>(url, this.nmsService.httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }

  listItem(id: number): Observable<any> {
    const url = env.baseUrl + this.listItemUrl;
    return this.httpClient.put(url, { id }, this.nmsService.httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }

  unListItem(id: number): Observable<any> {
    const url = env.baseUrl + this.unListItemUrl;
    return this.httpClient.put(url, { id }, this.nmsService.httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }

  deleteItem(id: number): Observable<any> {
    const url = env.baseUrl + this.deleteItemUrl.replace('{id}', id.toString());
    return this.httpClient.delete(url, this.nmsService.httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }


}
