# Resolver

## `Resolver` vs `Promise` for user login page

- For user login, use a service with Observables (or Promises if you must), not a Resolver.
- Resolvers are useful when a component needs data ready before it's displayed — not when posting data like login credentials.
- Resolver happens before routing begins.

## `Resolver`

An Angular resolver is a class that implements the Resolve interface, used to pre-fetch data before a route is activated. This
ensures that the component has the necessary data before it is rendered, improving user experience by preventing loading delays.

### 1/3. define a Resolver

    import { Injectable } from '@angular/core';
    import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
    import { Observable, of } from 'rxjs';
    import { catchError } from 'rxjs/operators';
    import { UserService } from './user.service';
    
    interface User {
      id: number;
      name: string;
      email: string;
    }
    @Injectable({
      providedIn: 'root',
    })
    export class UserResolver implements Resolve<User> {
      constructor(private userService: UserService) {}
    
      resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<User> {
        const userId = route.paramMap.get('id');
        if (userId) {
          return this.userService.getUser(parseInt(userId, 10)).pipe(
            catchError(() => {
              // Handle error appropriately, e.g., redirect to an error page
              return of({} as User);
            })
          );
        } else {
          return of({} as User);
        }
      }
    }

### 2/3. Route config

    import { NgModule } from '@angular/core';
    import { RouterModule, Routes } from '@angular/router';
    import { UserComponent } from './user.component';
    import { UserResolver } from './user.resolver';
    
    const routes: Routes = [
      {
        path: 'users/:id',
        component: UserComponent,
        resolve: {
          user: UserResolver,
        },
      },
    ];
    
    @NgModule({
      imports: [RouterModule.forRoot(routes)],
      exports: [RouterModule],
    })
    export class AppRoutingModule {}

### 3/3. Access the data in component (Using `ActivatedRoute`)

    import { Component, OnInit } from '@angular/core';
    import { ActivatedRoute } from '@angular/router';
    
    interface User {
      id: number;
      name: string;
      email: string;
    }

    @Component({
      selector: 'app-user',
      template: `
        <div *ngIf="user">
          <h1>{{ user.name }}</h1>
          <p>Email: {{ user.email }}</p>
        </div>
      `,
    })
    export class UserComponent implements OnInit {
      user: User | undefined;
    
      constructor(private route: ActivatedRoute) {}
    
      ngOnInit(): void {
        this.route.data.subscribe(data => {
          this.user = data['user'];
        });
      }
    }

- Use Resolvers when you want route transitions to wait for essential data.
- Use Observables in ngOnInit() when you want a more dynamic and responsive UI.

## `ResolverFn`

[Official Resolver]<https://angular.io/api/router/ResolveFn>

[Medium Resolver]<https://medium.com/codex/functional-resolvers-in-angular-43951c50c4e>

Resolvers in Angular are data providers that are used to pre-fetch data when the user is navigating through
the application before Angular has rendered the desired page; i.e. it blocks the navigation until it’s resolved.
The resolved data is accessible in the component through the ActivatedRoute class.

Previously we used to generate a resolver(a service file) and implement the Resolve class.

But as per the latest official Angular docs, it clearly mentions:

    Class-based Route resolvers are deprecated in favor of functional resolvers. An injectable class can be used
    as a functional guard using the inject function

So now we do not need to generate a resolver(service file) using the CLI and implement the Resolve class. It rather got a lot simpler.

Here’s how:
