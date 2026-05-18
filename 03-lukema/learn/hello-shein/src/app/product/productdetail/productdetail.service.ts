import { NmsService } from '@/app/utils/services/nms.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { env } from 'environments/environment';
import { Observable, catchError } from 'rxjs';

export interface Product {
  id?: number,
  name: string,
  description?: string | null,
  price?: number,
  imageUrlPrefix?: string | null,
  images?: ProductImage[],
  imageLinks?: string[],
}

export interface ProductImage {
  id: number,
  fileName: string,
}

@Injectable()
export class ProductdetailService {

  constructor(private httpClient: HttpClient,
    private nmsService: NmsService) { }

  private getUrl = '/spring/shein/getProduct/{id}';

  doGetProduct(productId: Number): Observable<any> {
    const url = env.baseUrl + this.getUrl.replace('{id}', productId.toString());
    return this.httpClient.get<Product>(url, this.nmsService.httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }

}
