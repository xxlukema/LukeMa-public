import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, NavigationEnd, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { filter, Observable } from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class MyResolveService implements Resolve<string> {

  constructor(router: Router) {
    router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event) => {
        if (event instanceof NavigationEnd) {
          this.previousUrl = event.url;

          if (this.previousUrl === '/home') {
            // `this.from` is set by `HomeComponent` to `this.myResolveService.setFrom('Home Page');`
          } else {
            // reset `this.from` to `this.previousUrl`
            this.from = this.previousUrl;
          }
        }
      });
  }

  previousUrl = '';
  from = '';

  resolve(route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): string | Observable<string> | Promise<string> {

    if (this.from === 'Home Page') {
      return this.from;
    } else {
      const from = route.routeConfig?.data ? route.routeConfig?.data['from'] : undefined;
      if (from) {
        this.from = from;
        console.debug('default `data.from` in ?-routing.module.ts (if it exists):', from);
      }
      return this.previousUrl;
    }
  }

  setFrom(from: string): void {
    this.from = from;
  }

}
