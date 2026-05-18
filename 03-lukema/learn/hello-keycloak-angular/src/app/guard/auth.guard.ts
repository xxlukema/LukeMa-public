import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  Router,
  RouterStateSnapshot,
} from "@angular/router";
import { KeycloakAuthGuard, KeycloakService } from "keycloak-angular";

@Injectable({
  providedIn: "root",
})
export class AuthGuard extends KeycloakAuthGuard {
  constructor(
    protected readonly router: Router,
    protected readonly keycloakService: KeycloakService
  ) {
    super(router, keycloakService);
  }

  public async isAccessAllowed(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Promise<boolean> {

    /**
     * TODO: Keep
     */
    /*
    this.channel$ = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(
      (event) => {
        if (this.router.url === '/login' && event['url'] === '/') {
          this.channel$?.unsubscribe();
        }
      });
    */

    if (!this.authenticated) {
      await this.keycloakService.login({
        redirectUri: window.location.origin + state.url,
      });
    }

    const requiredRoles = route.data.roles;

    console.log('---- guard requiredRoles', requiredRoles);
    console.log('---- guard this.roles', this.roles);

    if (!(requiredRoles instanceof Array) || requiredRoles.length === 0) {
      return true;
    }

    return requiredRoles.every((role) => this.roles.includes(role));
  }
}
