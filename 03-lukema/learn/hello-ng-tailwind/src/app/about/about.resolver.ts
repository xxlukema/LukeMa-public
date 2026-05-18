import { DestroyRef, inject, Injectable } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { catchError, firstValueFrom } from 'rxjs';
import { UserResponse, UserService } from '../contact/user.service';

/**
export const aboutResolver: ResolveFn<UserResponse> = (route, state): Observable<UserResponse> => {

  const userService = inject(UserService);
  const destroyRef = inject(DestroyRef);

  userService.getData().pipe(
    takeUntilDestroyed(destroyRef),
    catchError((err) => {
      console.error('aboutResolver', 'Error fetching data', err);
      return [err];
    })
  ).subscribe({
    next: (data) => {
      console.log('aboutResolver', 'data', data);
      return of(data);
    },
    error: (err) => {
      console.error('aboutResolver', 'Error fetching data', err);
      return of({} as UserResponse);
    },
    complete: () => {
      console.log('aboutResolver', 'complete');
    }
  });

  console.log('aboutResolver', 'before return');
  return of({} as UserResponse);;
};
*/

@Injectable({
  providedIn: 'root',
})
export class UserResolver implements Resolve<UserResponse> {
  constructor(private readonly userService: UserService) { }

  private readonly destroyRef = inject(DestroyRef);

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<UserResponse> {
    /**
     * Observable is not required for the resolver,
     * but it can be used to handle asynchronous data fetching.
     * The resolver will wait for the Observable to complete before navigating to the route.
     */
    /**
    this.userService.getData().pipe(
      takeUntilDestroyed(this.destroyRef),
      catchError((err) => {
        console.error('aboutResolver', 'Error fetching data', err);
        return [err];
      })
    ).subscribe({
      next: (data) => {
        console.log('aboutResolver', 'data', data);
        return of(data);
      },
      error: (err) => {
        console.error('aboutResolver', 'Error fetching data', err);
        return of({} as UserResponse);
      },
      complete: () => {
        console.log('aboutResolver', 'complete');
      }
    });

    console.log('aboutResolver', 'before return');
    return of({} as UserResponse);
    */

    /**
    return of({
      userId: 123,
      id: 1,
      title: 'title',
      body: 'body'
    } as UserResponse).pipe(
      takeUntilDestroyed(this.destroyRef),
      delay(1000), // Simulate a delay for demonstration purposes
    )
    */

    return firstValueFrom(
      this.userService.getData().pipe(
        takeUntilDestroyed(this.destroyRef),
        catchError((err) => {
          console.error('aboutResolver', 'Error fetching data', err);
          return [err];
        })
      )
    )
  }
}
