import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";

const routes: Routes = [
  {
    path: "",
    redirectTo: "home",
    pathMatch: "full",
  },
  {
    path: "home",
    loadChildren: () => import("./views/home/home.module").then((m) => m.HomeModule),
  },
  {
    path: "profile",
    loadChildren: () => import("./views/profile/profile.module").then((m) => m.ProfileModule),
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes,
      {
        relativeLinkResolution: 'legacy',
        onSameUrlNavigation: 'reload',
        scrollPositionRestoration: 'top',
        useHash: true,
      })
  ],
  exports: [RouterModule],
})
export class AppRoutingModule { }
