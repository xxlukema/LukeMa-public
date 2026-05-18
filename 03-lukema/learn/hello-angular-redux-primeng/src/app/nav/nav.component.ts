import * as FluxSlectors from '@/app/flux/flux.selector';
import { FluxData, FluxState } from '@/app/flux/flux.state';
import { flux2Selector } from '@/app/flux2/flux2.selector';
import { Flux2Data, Flux2State } from '@/app/flux2/flux2.state';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { select, Store } from '@ngrx/store';
import { MenuItem } from 'primeng/api';
import { Observable } from 'rxjs';
import { MyInjectable } from '../injectable/my-injectable';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css'],
})
export class NavComponent implements OnInit {
  constructor(
    private store: Store<FluxState>,
    private store2: Store<Flux2State>,
    public myInjectable: MyInjectable,
    private router: Router
  ) {
    this.fluxData = this.store.select(FluxSlectors.fluxSelector);
    this.flux2Data = this.store2.pipe(select(flux2Selector));
  }

  fluxData: Observable<FluxData>;
  flux2Data: Observable<Flux2Data>;
  menuItems!: MenuItem[];

  ngOnInit(): void {
    this.initMenuItems();
  }

  initMenuItems(): void {
    this.menuItems = [
      {
        label: 'Home',
        disabled: false,
        command: (event) => {
          this.router.navigate(['home']);
        },
      },
      {
        label: 'Process',
        icon: 'fas fa-fw fa-edit',
        items: [
          {
            label: 'Landing Zone',
            disabled: true,
            command: (event) => {
              this.router.navigate(['landingzone']);
            },
          },
          {
            label: 'Contact',
            disabled: false,
            command: (event) => {
              this.router.navigate(['contact']);
            },
          },
        ],
      },
    ];
  }
}
