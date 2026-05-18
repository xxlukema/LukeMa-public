import { NmsService } from '@/app/utils/services/nms.service';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { env } from 'environments/environment';
import { Observable, catchError } from 'rxjs';

export interface Country {
  id?: number,
  code: string,
  name: string,
}

export interface User {
  /**
   * email is username
   */
  id?: number | null | undefined,
  email: string | null | undefined,
  username?: string | null | undefined,
  password: string | null | undefined,
  firstname?: string | null | undefined,
  lastname?: string | null | undefined,
  phone?: string | null | undefined,
  businessname?: string | null | undefined,
  countryCode?: string | null | undefined,
  isBuyOnly?: boolean | null | undefined,
}

export interface RegisterResponse {
  code: number,
  statue: string,
  reason: string
}

@Injectable({
  providedIn: 'root',
})
export class RegisterService {

  constructor(private httpClient: HttpClient,
    private nmsService: NmsService) { }

  private getAllCountriesUrl = '/spring/shein/allCountries';

  private registerUrl = '/spring/user/register';

  getAllCountries(): Observable<any> {
    const url = env.baseUrl + this.getAllCountriesUrl;
    return this.httpClient.get<Country>(url, this.nmsService.httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }

  register(user: User): Observable<HttpResponse<any>> {
    const url = env.baseUrl + this.registerUrl;
    return this.httpClient.post<HttpResponse<any>>(url, user, this.nmsService.httpOptions).pipe(
      catchError(this.nmsService.handleError)
    );
  }


}
