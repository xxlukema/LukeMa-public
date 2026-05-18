class environment {
  private localHost = 'localhost';
  protected ec2DockerBootHost = 'ec2-54-198-112-75.compute-1.amazonaws.com';
  protected lukeebDockerBootHost = 'lukeebmyproperties2-env.eba-8aetkvp5.us-east-1.elasticbeanstalk.com';
  protected ebDockerBootHost = 'ec2-3-88-8-69.compute-1.amazonaws.com';

  private bootContextPath = '/my-properties-boot';

  get production() {
    return true;
  }

  public baseUrl = '/my-properties-ng/';
  public apiEndpoint: string;

  public authUrl = 'http://localhost:8083/auth/realms/test_realm/account/';
  public authRealm = 'nms';
  public authClientId = 'nms-client';

  constructor() {
    switch (location.hostname) {
      case this.localHost:
        if (location.port && location.port === '4200') {
          /**
           * Laptop local development
           */
          this.apiEndpoint = 'https://' + this.localHost + ':8443' + this.bootContextPath;
          // this.apiEndpoint = 'http://' + this.localHost + ':8080' + this.bootContextPath;
          // this.apiEndpoint = this.bootContextPath;
        } else {
          /**
           * Running tests on local tomcat docker
           */
          this.apiEndpoint = 'http://' + this.localHost + this.bootContextPath;
        }
        break;
      default:
        this.apiEndpoint = 'https://' + location.hostname + ':8443' + this.bootContextPath;
        break;
    }
  }
}

export const env = new environment();
