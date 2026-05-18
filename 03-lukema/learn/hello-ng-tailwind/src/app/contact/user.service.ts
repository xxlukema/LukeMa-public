import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';

export interface UserResponse {
  userId: number;
  id: number;
  title: string;
  body: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private readonly httpClient: HttpClient) { }

  /**
   * For testing purposes only
   */
  getData(): Observable<UserResponse> {
    return this.httpClient.get<UserResponse>('https://jsonplaceholder.typicode.com/posts/1').pipe(
      catchError((err) => {
        console.error('Error fetching data', err);
        return [err];
      })
    );
  }
}
