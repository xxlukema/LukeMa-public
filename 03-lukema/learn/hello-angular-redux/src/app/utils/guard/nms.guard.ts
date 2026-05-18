import { Injectable, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { Store } from '@ngrx/store';
import { KeycloakAuthGuard, KeycloakService } from 'keycloak-angular';
import * as UserActions from '@/app/shared/user/user.action';
import { userSelector } from '@/app/shared/user/user.selector';
import { UserState } from '@/app/shared/user/user.state';
import { User } from '@/app/shared/user/user.type';
import { Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NmsGuard extends KeycloakAuthGuard implements OnDestroy, OnInit {
  constructor(
    protected override readonly router: Router,
    protected readonly keycloakService: KeycloakService,
    private readonly store: Store<UserState>
  ) {
    super(router, keycloakService);

    this.user$ = this.store.select(userSelector).subscribe(
      item => {
        this.user = item;
      }
    );
  }

  user?: User;
  user$?: Subscription;
  channel$?: Subscription;

  ngOnInit(): void {
    this.keycloakService.enableBearerInterceptor;
  }

  public async isAccessAllowed(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Promise<boolean> {
    if (!this.authenticated) {
      /**
       * TODO: Keep
       */
      /**
      this.channel$ = this.router.events.pipe(
        filter(event => event instanceof NavigationEnd)
      ).subscribe(
        (event) => {
          if (this.router.url === '/login' && event['url'] === '/') {
            this.channel$?.unsubscribe();
          }
        });
      */
      await this.keycloakService.login({
        redirectUri: window.location.origin + state.url,
      });
    } else {
      if (!this.user?.username) {
        this.keycloakService.loadUserProfile().then(
          profile => {
            this.store.dispatch(UserActions.setUser({
              payload: {
                user: {
                  firstName: profile.firstName,
                  lastName: profile.lastName,
                  email: profile.email,
                  username: profile.username,
                }
              }
            }));
          }
        );
      }
      const requiredRoles = route.data['roles'];
      if (requiredRoles && requiredRoles.length > 0) {
        if (!(requiredRoles instanceof Array) || requiredRoles.length === 0) {
          return true;
        }
        return requiredRoles.every((role) => this.roles.includes(role));
      }
      return this.roles.length > 0;
    }
    return false;
  }

  ngOnDestroy(): void {
    if (this.user$) {
      this.user$.unsubscribe();
    }
  }


}

