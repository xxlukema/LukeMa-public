import { env } from '@/environments/environment';
import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/app.module';

/**
 * Bootstraping:
 *
 * 1. Specify the enviroment in which your Angular App is running
 * 2. Use the bootstrapModule() function to boot your entry module by supplying the module as an argument.
 * 3. Inside the root module, specify your entry point component in the module configuration object.
 */
if (env.production) {
  enableProdMode();
}

platformBrowserDynamic()
  .bootstrapModule(AppModule)
  .catch((err) => {
    console.log(err);
  });
