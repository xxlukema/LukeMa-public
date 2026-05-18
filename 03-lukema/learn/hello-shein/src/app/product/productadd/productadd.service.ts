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
export class ProductaddService {

  constructor(private httpClient: HttpClient,
    private nmsService: NmsService) { }

  private addUrl = '/spring/shein/addProduct';

  doAddProduct(formData: FormData): Observable<any> {
    const url = env.baseUrl + this.addUrl;
    return this.httpClient.post<Product>(url, formData,
      {
        reportProgress: true,
        observe: 'events',
        withCredentials: false,
        // headers: { 'Content-Type': 'multipart/form-data' }
      }
    ).pipe(
      catchError(this.nmsService.handleError)
    );
  }

}
