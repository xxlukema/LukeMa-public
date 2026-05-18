import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { CookieService } from 'ngx-cookie-service';


@Injectable({
  providedIn: 'root'
})
export class JwtCookieService {

  constructor(
    private cookieService: CookieService
  ) {
  }

  CookieName = 'shein-auth-jwt';


  /**
   * !!! important !!!
   * spring boot side: cookie.setHttpOnly(false): script can access the cookie.
   */
  isExist(): boolean {
    const cookie = this.cookieService.get(this.CookieName);

    if (cookie) {
      return true;
    }

    console.debug('Cookie not found: (1) Expired. (2) Not exist. or (3) If "cookie.setHttpOnly(true)", then so that script can NOT access the cookie.');

    return false;
  }

  getUsername(): string | null | undefined {
    const cookie = this.cookieService.get(this.CookieName);

    if (cookie) {
      const decoded = jwtDecode(cookie);
      if (decoded) {
        return decoded.sub;
      }
    }

    return null;
  }

  getValueByKey(key: string): string | null | undefined {
    const cookie = this.cookieService.get(this.CookieName);

    if (cookie) {
      const decoded = jwtDecode(cookie);
      if (decoded) {
        return decoded[key];
      }
    }

    return null;
  }

  getFirstname(): string | null | undefined {
    return this.getValueByKey('firstname');
  }

  getLastname(): string | null | undefined {
    return this.getValueByKey('lastname');
  }

  getPhone(): string | null | undefined {
    return this.getValueByKey('phone');
  }

  getEmail(): string | null | undefined {
    return this.getValueByKey('email');
  }

  getCountryCode(): string | null | undefined {
    return this.getValueByKey('countryCode');
  }

  isEnabled(): boolean | null | undefined {
    return this.getValueByKey('isEnabled') === 'true';
  }

  isBuyOnly(): boolean | null | undefined {
    return this.getValueByKey('isBuyOnly') === 'true';
  }

  isExpired(): boolean {
    const cookie = this.cookieService.get(this.CookieName);

    if (cookie) {
      const decoded = jwtDecode(cookie);
      if (decoded) {
        return decoded.exp ? (Math.round(decoded.exp - Date.now() / 1_000) <= 0) : true;
      }
    }

    return true;
  }
}
