export class Env {

  /**
   * Toggle true/false
   *
   * Local Test: true
   * Dev Test: false
   */
  isLocalTest = true;


  /**
   * Cypress Test Environment Settings
   */

  localUrl = 'http://localhost:4200';
  devUrl = 'https://ec2-18-253-51-232.us-gov-east-1.compute.amazonaws.com';

  get testTarget() {
    if (this.isLocalTest) {
      return this.localUrl;
    } else {
      return this.devUrl;
    }
  }

  constructor() { }

  keyCloakUrl = 'https://ec2-18-253-51-232.us-gov-east-1.compute.amazonaws.com';
  realm = 'nms';
  clientId = 'nms-client';

}

export const env = new Env();
