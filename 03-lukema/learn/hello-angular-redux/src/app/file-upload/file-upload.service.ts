import { CommonService } from '@/app/common/common.service';
import { env } from '@/environments/environment';
import { HttpClient, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FileUploadService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly commonService: CommonService
  ) {}

  uploadUrl = env.helloBaseUrl + '/spring/file-upload';

  public upload(formData: FormData): Observable<HttpEvent<any>> {
    return this.httpClient
      .post<HttpEvent<any>>(this.uploadUrl, formData, {
        reportProgress: true,
        observe: 'events',
      })
      .pipe(catchError(this.commonService.handleError));
  }
}
