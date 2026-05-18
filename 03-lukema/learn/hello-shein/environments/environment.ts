// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

// import { LayoutStyleBuilder } from '@ngbracket/ngx-layout';

class Env {
  get production() {
    return true;
  }

  // private baseUrl = 'http://localhost:8443';

  private baseUrlCore!: string;

  localHost = 'localhost';

  get baseUrl() {
    return this.baseUrlCore;
  }

  get imageUrlPrefix(): string {
    return this.baseUrlCore + '/content';
  }

  authUrl!: string;
  realm = 'nms';
  clientId = 'nms-client';

  constructor() {

    this.baseUrlCore = 'NOT SET';

    switch (location.hostname) {
      case this.localHost:
        this.baseUrlCore = 'https://' + this.localHost + ':8443';
        break;
      default:
        this.baseUrlCore = 'https://' + location.host;
        // this.baseUrlCore = 'https://' + location.host + ':443';
        break;
    }

  }
}

export const env = new Env();
