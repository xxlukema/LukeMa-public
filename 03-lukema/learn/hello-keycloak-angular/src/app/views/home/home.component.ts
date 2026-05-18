import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { KeycloakService } from "keycloak-angular";
import { KeycloakProfile } from "keycloak-js";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"],
})
export class HomeComponent implements OnInit {
  constructor(
    private router: Router,
    private readonly keycloakService: KeycloakService
  ) {}

  isLoggedIn = false;
  public keycloakProfile: KeycloakProfile | null = null;

  async ngOnInit() {
    this.isLoggedIn = await this.keycloakService.isLoggedIn();

    if (this.isLoggedIn) {
      this.keycloakProfile = await this.keycloakService.loadUserProfile();
    }
  }

  forwardToProfilePage(): void {
    this.router.navigateByUrl("/profile");
  }

  public login() {
    this.keycloakService.login();
  }

  logout(): void {
    this.keycloakService.logout("http://localhost:4200");
    // this.keycloakService.logout("https://localhost");
  }
}
