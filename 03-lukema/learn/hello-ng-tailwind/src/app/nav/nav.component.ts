import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgIcon, provideIcons } from '@ng-icons/core';
import { featherAirplay } from '@ng-icons/feather-icons';
import { heroUser } from '@ng-icons/heroicons/outline';

@Component({
  selector: 'app-nav',
  imports: [
    RouterLink,
    NgIcon
  ],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css',
  viewProviders: [provideIcons({ featherAirplay, heroUser })]
})
export class NavComponent {

}
