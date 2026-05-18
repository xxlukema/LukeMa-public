// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

// import { LayoutStyleBuilder } from '@ngbracket/ngx-layout';

class Env {
  get production() {
    return true;
  }

  authUrl = 'https://localhost:443/auth';
  realm = 'nms';
  clientId = 'nms-client';

  testGetUrl = 'https://api.github.com/users/seeschweiler';
  testPostUrl = 'http://jsonplaceholder.typicode.com/posts';
  helloSslBaseUrl = 'https://localhost:8443';
  helloBaseUrl = 'http://localhost:8080';
  baseUrl = 'http://localhost:8080';

  /**
   * mapbox
   */
  mapbox = {
    accessToken:
      'pk.eyJ1IjoibHVrZW1hbCIsImEiOiJjbDByOWIyajIwMWJnM2Vxd3Jxdm9uc2gwIn0.z4EqQadsvxs6RA67PEPElg',
    url: 'https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}',
    attribution: 'Map data &copy; <a href="https://www.mapbox.com/about/maps/">Mapbox</a>' +
      ' &copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>' +
      '<strong><a href="https://www.mapbox.com/map-feedback/" target="_blank">Improve this map</a></strong>',
    idStreet: 'mapbox/streets-v11',
    idSatellite: 'mapbox/satellite-v9',
  };

  /**
   * openstreetmap online map
   */
  openStreet = {
    url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
    copyright:
      'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
      '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>',
  };

  /**
   * openstreetmap offline map serving on docker
   */
  openStreetLuke = {
    url: 'http://localhost:8080/tile/{z}/{x}/{y}.png',
    copyright: this.openStreet.copyright
  };

  /**
   * Google map
   *
   * https://gis.stackexchange.com/questions/225098/using-google-maps-static-tiles-with-leaflet
   */
  googleMap = {
    lyrs: {
      street: 'm',
      hybrid: 's,h',
      satellite: 's',
      terrain: 'p',
      road: 'h',
      alteredRoad: 'r'
    },
    url: 'https://{s}.google.com/vt/lyrs={lyrs}&x={x}&y={y}&z={z}',
    subdomains: ['mt0', 'mt1', 'mt2', 'mt3'],
    attribution: 'Map data &copy; <a href="https://maps.google.com/">Google Maps</a>',
    traffic: '@221097413,traffic',  /** Example: https://mt0.google.com/vt/lyrs=m@221097413,traffic&x={x}&y={y}&z={z} */
  };
}

export const env = new Env();
