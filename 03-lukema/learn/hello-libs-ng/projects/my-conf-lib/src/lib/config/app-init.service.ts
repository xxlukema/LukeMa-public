import { Inject, Injectable } from '@angular/core';
import { APP_CONFIG, MY_CONFIG } from './config';


@Injectable({
  providedIn: 'root'
})
export class AppInitService {

  constructor(@Inject(APP_CONFIG) private appConfig: any, @Inject(MY_CONFIG) private myConfig: any) { }

  slower() {

    /**
     * If you return reject from the service, the angular app will not start.
     */
    return new Promise<void>((resolve, reject) => {
      console.log('AppInitService.slower() called', 'slower', 'Sleeping for 3 seconds...');

      // do your initialisation stuff here

      setTimeout(() => {
        console.log('app-init.service', 'slower', 'AppInitService Finished after', 'faster');

        this.appConfig.APP_INITIALIZER = 'APP_INITIALIZER slower';
        this.appConfig.override = 'override slower';

        console.log('AppInitService.slower()', 'appConfig', this.appConfig);
        resolve();
      }, 3_000);

    });
  }

  faster() {

    /**
     * If you return reject from the service, the angular app will not start.
     */
    return new Promise<void>((resolve, reject) => {
      console.log('AppInitService.faster() called', 'faster', 'Sleeping for 1 seconds...');

      // do your initialisation stuff here

      setTimeout(() => {
        console.log('app-init.service', 'faster', 'AppInitService Finished before', 'slower');

        this.appConfig.APP_INITIALIZER = 'APP_INITIALIZER faster';
        this.appConfig.override = 'override faster';

        console.log('AppInitService.faster()', 'appConfig', this.appConfig);
        resolve();
      }, 1_000);

    });
  }
}
