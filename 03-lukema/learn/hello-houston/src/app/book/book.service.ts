import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom, Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';


export class Book {
    id: number;
    name: string;
    constructor() { }
}

@Injectable()
export class BookService {
    url_books = environment.baseUrl + '/book/books';
    url_add = environment.baseUrl + '/book/add';

    private httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    constructor(private httpClient: HttpClient) { }

    getBooksWithObservable(): Observable<Book[]> {
        return this.httpClient.get<Book[]>(this.url_books)
            .pipe(
                // catchError(this.handleErrorObservable)
            );
    }

    addBookWithObservable(book: Book): Observable<Book> {
        return this.httpClient.post<Book>(this.url_add, book, this.httpOptions)
            .pipe(
                // catchError(this.handleErrorObservable)
            );
    }

    async getBooksWithPromise(): Promise<Book[]> {
        return await lastValueFrom(this.httpClient.get<Book[]>(this.url_books))
            .catch(this.handleErrorPromise);
    }

    addBookWithPromise(book: Book): Promise<Book> {
        return this.httpClient.post<Book>(this.url_add, book, this.httpOptions).toPromise()
            .catch(this.handleErrorPromise);
    }

    private handleErrorObservable(error: HttpErrorResponse) {
        console.error(error.message || error);
        throwError(() => error.message ?? error);
    }

    private handleErrorPromise(error: HttpErrorResponse) {
        console.error(error.message || error);
        return Promise.reject(error.message || error);
    }
}
