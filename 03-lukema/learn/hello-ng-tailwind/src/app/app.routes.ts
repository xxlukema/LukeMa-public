import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { PageNotFoundComponent } from './page-not-found.component';
import { authGuard } from './utils/guard/auth.guard';
import { UserResolver } from './about/about.resolver';


export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    title: 'Login',
    data: { description: 'Welcome to the login page' }
  },
  {
    path: 'logout',
    component: LogoutComponent,
    title: 'Logout',
    data: { description: 'Welcome to the logout page' }
  },
  {
    path: 'home',
    loadComponent: () => import('./home/home.component').then(m => m.HomeComponent),
    canActivate: [authGuard], // Example of a guard
    title: 'Home',
    data: { description: 'Welcome to the home page' }
  },
  {
    path: 'about',
    loadComponent: () => import('./about/about.component').then(m => m.AboutComponent),
    /**
     * Lazy loading of resolver does not work!
     */
    // resolve: { user: () => import('./about/about.resolver').then(m => m.UserResolver) }, // Example of a resolver
    resolve: { user: UserResolver }, // Example of a resolver
    canActivate: [authGuard],
    title: 'About Us',
    data: { description: 'Learn more about us' }
  },
  {
    path: 'contact',
    loadComponent: () => import('./contact/contact.component').then(m => m.ContactComponent),
    canActivate: [authGuard],
    title: 'Contact Us',
    data: { description: 'Get in touch with us' }
  },
  {
    path: 'window-size',
    loadComponent: () => import('./window-size/window-size.component').then(m => m.WindowSizeComponent),
    canActivate: [authGuard],
    title: 'Window Size',
    data: { description: 'Check the window size' }
  },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent } //Wildcard route
];
