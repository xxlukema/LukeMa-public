import { NmsService } from '@/app/utils/services/nms.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { env } from 'environments/environment';
import { Observable } from 'rxjs';
import { User } from '@/app/user/register/register.service';

export interface SigninUser {
  /**
   * email is username
   */
  username: string | null | undefined,
  password: string | null | undefined,
}


@Injectable({
  providedIn: 'root',
})
export class SigninService {

  constructor(private httpClient: HttpClient,
    private nmsService: NmsService) { }

  private signinUrl = '/spring/user/signin';

  signin(user: SigninUser): Observable<User> {
    const url = env.baseUrl + this.signinUrl;
    return this.httpClient.post<User>(url, user, this.nmsService.httpOptions).pipe(
      // catchError(this.nmsService.handleError)
    );
  }


}
