import { Product } from '@/app/product/productadd/productadd.service';
import { NmsService } from '@/app/utils/services/nms.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { env } from 'environments/environment';
import { Observable, catchError } from 'rxjs';

@Injectable()
export class ProductlistService {

  constructor(private httpClient: HttpClient,
    private nmsService: NmsService) { }

  private listUrl = '/spring/shein/listProducts';

  doListProducts(): Observable<Product[]> {
    const url = env.baseUrl + this.listUrl;
    return this.httpClient.get<Product[]>(url, this.nmsService.httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }

}

