import { CommonModule } from '@angular/common';
import { APP_INITIALIZER, ModuleWithProviders, NgModule } from '@angular/core';
import { MyConfLibService } from '../my-conf-lib.service';
import { AppInitService } from './app-init.service';
import { APP_CONFIG, MY_CONFIG } from './config';


/**
 * Preload app config service. This should be executed after dep MyConfLibService.
 */
export function init_conf(myConfLibService: MyConfLibService) {
  console.log('config.module', 'init_conf', 'Preload app config service. This should be executed after dep MyConfLibService.');

  return (): void => {
    return myConfLibService.sayHello();
  }
}

export function init_app_slower(appInitService: AppInitService) {
  console.log('config.module', 'init_app_slower', 'Preload app config service. This should be executed after dep AppInitService.');

  return (): Promise<any> => {
    return appInitService.slower();
  }
}

export function init_app_faster(appInitService: AppInitService) {
  console.log('config.module', 'init_app_faster', 'Preload app config service. This should be executed after dep AppInitService.');

  return (): Promise<any> => {
    return appInitService.faster();
  }
}

@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class ConfigModule {
  static forRoot(appConfig: any, myConfig?: any): ModuleWithProviders<ConfigModule> {
    return {
      ngModule: ConfigModule,
      providers: [
        /**
         * For consumers of hello-libs-ng library:
         * 
         * APP_CONFIG and MY_CONFIG are invoked after web app bootstrap process, whenever @Inject(APP_CONFIG) and @Inject(MY_CONFIG)
         * are injected by a constructor of a page component. They are not part of bootstrap process.
         * 
         * constructor(@Inject(APP_CONFIG) private appConfig: any, @Inject(MY_CONFIG) private myConfig: any) { }
         */
        {
          provide: APP_CONFIG,
          useValue: appConfig,
          multi: false
        },
        {
          provide: MY_CONFIG,
          useValue: myConfig,
          multi: false
        },
        /**
         * For consumers of hello-libs-ng library:
         * 
         * APP_INITIALIZER is invoked at web app bootstrap process.
         * 
         * 1. ConfigModule.forRoot(environment, 'path config') must be imported in app.module.ts to invoke APP_INITIALIZER(s)'.
         * 2. If ConfigModule.forRoot(environment, 'path config') is imported from home.module.ts, then the APP_INITIALIZER(s) will not be invoked.
         */
        {
          provide: APP_INITIALIZER,
          useFactory: init_conf,
          deps: [MyConfLibService],
          multi: true
        },
        {
          provide: APP_INITIALIZER,
          useFactory: init_app_slower,
          deps: [AppInitService],
          multi: true
        },
        {
          provide: APP_INITIALIZER,
          useFactory: init_app_faster,
          deps: [AppInitService],
          multi: true
        }
      ]
    };
  }
}
