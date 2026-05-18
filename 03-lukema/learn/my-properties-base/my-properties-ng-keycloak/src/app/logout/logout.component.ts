import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss'],
})
export class LogoutComponent implements OnInit {
  constructor() {
    /**
     * Keycloak Login
     */

    // let action = "login";
    // let action = "register";
    let action = 'logout';

    const keycloakBaseUrl = 'https://localhost:9443';
    const keycloakRealm = 'nms';
    const keycloakClientId = 'nms-client';
    const keycloakBasePath =
      keycloakBaseUrl +
      '/auth/realms/' +
      keycloakRealm +
      '/protocol/openid-connect';

    switch (action) {
      case 'login':
        const keycloakLoginPath = keycloakBasePath + '/auth';

        const loginParams = new URLSearchParams();
        loginParams.set('client_id', keycloakClientId);
        loginParams.set('response_mode', 'fragment');
        loginParams.set('response_type', 'code');
        loginParams.set('login', 'true');

        const keycloakLoginUrl =
          keycloakLoginPath + '?' + loginParams.toString();
        window.location.href = keycloakLoginUrl;
        break;
      case 'register':
        const keycloakRegisterPath = keycloakBasePath + '/registrations';

        const registerParams = new URLSearchParams();
        registerParams.set('client_id', keycloakClientId);
        registerParams.set('response_mode', 'fragment');
        registerParams.set('response_type', 'code');

        const keycloakRegisterUrl =
          keycloakRegisterPath + '?' + registerParams.toString();
        window.location.href = keycloakRegisterUrl;
        break;
      case 'logout':
        const keycloakLogoutPath = keycloakBasePath + '/logout';
        const logoutParams = new URLSearchParams();
        /*
        logoutParams.set('client_id', keycloakClientId);
        logoutParams.set('response_mode', 'fragment');
        logoutParams.set('response_type', 'code');
        logoutParams.set('login', 'false');
        */
       // logoutParams.set('redirect_uri', 'http%3A%2F%2F127.0.0.1%3A4200%2F%23%2Fhome');
        const keycloakLogoutUrl =
          keycloakLogoutPath + '?' + logoutParams.toString();
        window.location.href = keycloakLogoutUrl;
        break;
      default:
        break;
    }
  }

  ngOnInit(): void {}
}
