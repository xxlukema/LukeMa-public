import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { MatToolbar, MatToolbarRow } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss'],
  imports: [
    CommonModule,
    MatToolbar,
    MatToolbarRow,
    MatIcon,
    RouterModule
  ]
})
export class NavComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    console.log('NavComponent initialized');
  }

}
