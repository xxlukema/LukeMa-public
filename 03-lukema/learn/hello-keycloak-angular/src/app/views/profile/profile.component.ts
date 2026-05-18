import { Component, OnInit } from "@angular/core";
import { KeycloakService } from "keycloak-angular";
import { KeycloakProfile } from "keycloak-js";

@Component({
  selector: "app-profile",
  templateUrl: "./profile.component.html",
  styleUrls: ["./profile.component.scss"],
})
export class ProfileComponent implements OnInit {
  user = "";
  public isLoggedIn = false;
  public keycloakProfile: KeycloakProfile | null = null;

  constructor(private readonly keycloakService: KeycloakService) {}

  ngOnInit(): void {
    this.initializeUserOptions();
  }

  private async initializeUserOptions() {
    this.user = this.keycloakService.getUsername();

    this.isLoggedIn = await this.keycloakService.isLoggedIn();

    if (this.isLoggedIn) {
      this.keycloakProfile = await this.keycloakService.loadUserProfile();
    }
  }

  logout(): void {
    this.keycloakService.logout("http://localhost:4200");
    // this.keycloakService.logout("https://localhost");
  }
}
