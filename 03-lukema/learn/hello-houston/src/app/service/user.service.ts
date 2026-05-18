import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom, Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { AppConstants } from '../app.constants';


export class User {
    id = -1;
    username: string;
    password: string;
    email: string;
    phone: string;

    constructor() {
    }
}

@Injectable()
export class UserService {

    private httpHeaders = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    loginUserUrl = environment.baseUrl + '/user/login';
    addUserUrl = environment.baseUrl + '/user/add';
    pingUserUrl = environment.baseUrl + '/user/ping';

    private myUser = new User();

    constructor(private httpClient: HttpClient) {
        this.logonFromLocalStorageObservable();
        // this.logonFromLocalStoragePromise();
        console.log('UserService constructor(). logonFromLocalStorage() called.');
    }

    logonFromLocalStorageObservable() {
        console.log('UserService logonFromLocalStorage() getting user from localStorage.');
        const user: User = this.getUserFromLocalStorage();
        if (user == null) {
            console.log('UserService logonFromLocalStorageObservable no user from localStorage.');
        } else {
            console.log('UserService logonFromLocalStorageObservable got user from localStorage. Doing user logon...');
            this.loginUserWithObservable(user).subscribe(
                (data: User) => {
                    console.log('UserService logonFromLocalStorageObservable() received: ' + JSON.stringify(data));
                    this.user = data;
                },
                (err: HttpErrorResponse) => {
                    if (err.error instanceof Error) {
                        console.log('UserService Client-side error occured: ' + err.error);
                    } else {
                        console.log('UserService Server-side error occured: ' + err.error);
                    }
                },
                () => {
                    console.log('UserService completed user logon.');
                }
            );
        }
    }

    async logonFromLocalStoragePromise() {
        console.log('UserService logonFromLocalStorage() getting user from localStorage.');
        const user: User = this.getUserFromLocalStorage();
        if (user == null) {
            console.log('UserService logonFromLocalStoragePromise no user from localStorage.');
        } else {
            console.log('UserService logonFromLocalStoragePromise got user from localStorage. Doing user logon...');
            lastValueFrom(this.loginUserWithObservable(user)).then(
                data => {
                    console.log('UserService.logonFromLocalStoragePromise() received: ' + JSON.stringify(data));
                    this.user = data;
                    // this.router.navigateByUrl('summary');
                    console.log(data);
                    console.log('UserService completed user logon.');
                },
                (error: any) => {
                    console.error('UserService got error Error.', error);
                }
            );
        }
    }

    get user(): User {
        return this.myUser;
    }

    set user(user: User) {
        this.myUser = user;
    }

    get isLoggedIn(): boolean {
        return this.user.id !== -1 && this.user.id !== -2;
    }

    getUsersWithObservable(): Observable<User[]> {
        return this.httpClient.get<User[]>(this.addUserUrl)
            .pipe(
                catchError(this.handleErrorObservable)
            );
    }

    loginUserWithObservable(user: User): Observable<User> {
        return this.httpClient.post<User>(this.loginUserUrl, user, this.httpHeaders)
            .pipe(
                catchError(this.handleErrorObservable)
            );
    }

    logout(): void {
        this.removeUserFromLocalStorage();
        this.myUser = new User();
    }

    saveUserToLocalStorage(user: User): void {
        if (user != null) {
            localStorage.setItem(AppConstants.CURRENT_USER, JSON.stringify(user));
            console.log('UserService User saved to localStorage.');
        }
    }

    removeUserFromLocalStorage(): void {
        localStorage.removeItem(AppConstants.CURRENT_USER);
        console.log('User removed from localStorage.');
    }

    getUserFromLocalStorage(): User {
        const userStr: string = localStorage.getItem(AppConstants.CURRENT_USER);
        if (userStr == null) {
            console.log('UserService User not found from localStorage.');
            return null;
        } else {
            console.log('User found from localStorage.');
            const user: User = JSON.parse(userStr);
            return user;
        }
    }

    addUserWithObservable(user: User): Observable<User> {
        return this.httpClient.post<User>(this.addUserUrl, user, this.httpHeaders)
            .pipe(
                catchError(this.handleErrorObservable)
            );
    }

    pingUserWithPromise(): Promise<User[]> {
        return this.httpClient.get<User[]>(this.pingUserUrl).toPromise()
            .catch(this.handleErrorPromise);
    }

    addUserWithPromise(user: User): Promise<User> {
        return this.httpClient.post<User>(this.addUserUrl, user, this.httpHeaders).toPromise()
            .catch(this.handleErrorPromise);
    }

    private handleErrorObservable(error: HttpErrorResponse) {
        const errMsg = (error.message) ? error.message :
            error.status ? `${error.status} - ${error.statusText}` : 'Server error';
        console.error(errMsg);
        console.error('UserService handleErrorObservable: ' + error.message || error);
        return throwError(() => error.message ?? error);
    }

    private handleErrorPromise(error: HttpErrorResponse) {
        const errMsg = (error.message) ? error.message :
            error.status ? `${error.status} - ${error.statusText}` : 'Server error';
        console.error(errMsg);
        console.error('UserService handleErrorPromise: ' + error.message || error);
        return Promise.reject(error.message || error);
    }
}
