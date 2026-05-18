import { APP_INITIALIZER, NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { KeycloakAngularModule, KeycloakService } from "keycloak-angular";
import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";

//function initializeKeycloak(keycloakService: KeycloakService) : ()=>Promise<boolean> {

const initializeKeycloak = (
  keycloakService: KeycloakService
): (() => Promise<boolean>) => {
  return () =>
    keycloakService.init({
      config: {
        // url: 'https://ec2-18-253-51-232.us-gov-east-1.compute.amazonaws.com:8443/auth',
        url: "https://localhost:9443/auth",
        // url: "https://localhost/auth",
        // url: "http://engddevvm6.hughes.com:39443/auth",
        realm: "nms",
        clientId: "nms-client",
      },
      initOptions: {
        checkLoginIframe: true,
        checkLoginIframeInterval: 25,
      },
      loadUserProfileAtStartUp: true,
    });
};

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, AppRoutingModule, KeycloakAngularModule],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
