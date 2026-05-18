import { Component, HostListener, OnInit } from '@angular/core';

@Component({
  standalone: false,
  selector: 'app-window-size',
  templateUrl: './window-size.component.html',
  styleUrls: ['./window-size.component.scss']
})
export class WindowSizeComponent implements OnInit {

  constructor() { }

  innerWidth: number = 0;
  innerHeight: number = 0;

  ngOnInit(): void {
    this.innerWidth = window.innerWidth;
    this.innerHeight = window.innerHeight;
  }

  @HostListener('window:resize', ['$event'])
  onResize($event: any) {
    this.innerWidth = window.innerWidth;
    this.innerHeight = window.innerHeight;
  }

}
