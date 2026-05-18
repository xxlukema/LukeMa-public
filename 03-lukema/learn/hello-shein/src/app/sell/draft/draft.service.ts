import { CategoryConditions } from '@/app/item/item.service';
import { NmsService } from '@/app/utils/services/nms.service';
import { HttpClient, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { env } from 'environments/environment';
import { Observable, catchError, last } from 'rxjs';

export interface Item {
  id?: number | null | undefined,
  title: string,
  category: string,
  condition: string,
  brand: string,

  optionalAttributes?: { [key: string]: any },

  description?: string | null | undefined,
  imageFileNames?: string[],
  price?: number | null,
  discount?: number | null,
  availableUnitQuantity?: number;
  soldUnitQuantity?: number;
  sellerUsername?: string | null | undefined,
  dateUpdated: number,
  status: string,
}

@Injectable()
export class DraftService {

  constructor(private httpClient: HttpClient,
    private nmsService: NmsService) { }

  private addItemUrl = '/spring/shein/addItem';

  private getConditionsByCategoryUrl = '/spring/shein/getConditionsByCategory/{category}';

  doAddItem(formData: FormData): Observable<HttpEvent<Item>> {
    const url = env.baseUrl + this.addItemUrl;
    return this.httpClient.post<Item>(url, formData,
      {
        reportProgress: true,
        observe: 'events',
        withCredentials: false,
        /**
         * !!! Trick
         *
         * The following line will cause: "Caused by: org.apache.tomcat.util.http.fileupload.FileUploadException: the request was rejected because no multipart boundary was found"
         *
         * `headers: { 'Content-Type': 'multipart/form-data' }`
         */
        // headers: { 'Content-Type': 'multipart/form-data' }
      }
    ).pipe(
      last(),
      catchError(this.nmsService.handleError)
    );
  }

  getConditionsByCategory(category: string): Observable<CategoryConditions> {
    const url = env.baseUrl + this.getConditionsByCategoryUrl.replace('{category}', category);

    return this.httpClient.get<CategoryConditions>(url, this.nmsService.httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }

  getImage(imageFileName: string): Observable<Blob> {
    return this.httpClient.get(env.imageUrlPrefix + '/' + imageFileName, { responseType: 'blob' });
  }

}
