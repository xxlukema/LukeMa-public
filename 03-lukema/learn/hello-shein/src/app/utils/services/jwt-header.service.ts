import { Injectable, OnDestroy, OnInit } from '@angular/core';
import { JwtPayload, jwtDecode } from 'jwt-decode';
import { Subject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class JwtHeaderService implements OnInit, OnDestroy {

  constructor(
  ) {
  }

  private JwtTokenName = 'shein-auth-jwt';
  private readonly destroyed$ = new Subject<void>();
  private jwtTokenDecodedPrivate: JwtPayload | null | undefined = null;
  private jwtTokenEncodedPrivate: string | null | undefined = null;

  /**
   * It is faster to communicate between components using **injected service**. However, communicating
   * using Event is selected for the purpose of **learning**.
   */
  private usernamePrivate: string | null | undefined = null;

  /**
   * It is faster to communicate between components using **injected service**. However, communicating
   * using EventService is selected, purely for **learning** purpose.
   */
  private firstnamePrivate: string | null | undefined = null;

  /**
   * client envoke this with `const username = this.jwtService.username;`
   */
  get username(): string | null | undefined {
    return this.usernamePrivate;
  }

  /**
   * client envoke this with `this.jwtService.username = 'my_username';`
   */
  set username(username: string | null | undefined) {
    this.usernamePrivate = username;
  }

  get firstname(): string | null | undefined {
    return this.firstnamePrivate;
  }

  set firstname(firstname: string | null | undefined) {
    this.firstnamePrivate = firstname;
  }

  ngOnInit(): void {
    const val = localStorage.getItem(this.JwtTokenName);
    if (val) {
      this.jwtTokenDecodedPrivate = jwtDecode(val);
    } else {
      this.jwtTokenDecodedPrivate = null;
    }
  }

  ngOnDestroy(): void {
    this.destroyed$.next();
    this.destroyed$.complete();
  }

  get jwtTokenEncoded(): string | null | undefined {
    return this.jwtTokenEncodedPrivate;
  }

  /**
   * TODO
   */
  /*
  store(token: string) : Observable<string> {
  }
  */

  storeToken(jwtToken: string) {
    if (jwtToken) {
      localStorage.setItem(this.JwtTokenName, jwtToken);
      this.jwtTokenEncodedPrivate = jwtToken;
    } else {
      this.clearToken();
    }
  }

  signoff() {
    localStorage.removeItem('username');
    localStorage.removeItem('firstname');
    this.clearToken();
  }

  clearToken() {
    localStorage.removeItem(this.JwtTokenName);
    this.jwtTokenDecodedPrivate = null;
    this.jwtTokenEncodedPrivate = null;
  }

  isTokenExist(): boolean {
    return this.jwtTokenDecodedPrivate == null;
  }

  /**
   * client envoke this with `const username = this.jwtService.getUsername();`
   */
  getUsername(): string | null | undefined {
    if (this.jwtTokenDecodedPrivate) {
      return this.jwtTokenDecodedPrivate.sub;
    }
    return null;
  }

  getValueByKey(key: string): string | null | undefined {
    if (this.jwtTokenDecodedPrivate) {
      return this.jwtTokenDecodedPrivate[key];
    }
    return null;
  }

  /**
   * client envoke this with `const firstname = this.jwtService.getFirstname();`
   */
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

  isTokenExpired(): boolean {
    if (this.jwtTokenDecodedPrivate) {
      return this.jwtTokenDecodedPrivate.exp ? (Math.round(this.jwtTokenDecodedPrivate.exp - Date.now() / 1_000) <= 0) : true;
    }
    return true;
  }

}
