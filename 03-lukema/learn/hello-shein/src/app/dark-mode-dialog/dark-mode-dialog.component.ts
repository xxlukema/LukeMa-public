import { Component, OnInit } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'app-dark-mode-dialog',
  standalone: true,
  imports: [
    MatDialogModule
  ],
  templateUrl: './dark-mode-dialog.component.html',
  styleUrls: ['./dark-mode-dialog.component.scss']
})
export class DarkModeDialogComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
