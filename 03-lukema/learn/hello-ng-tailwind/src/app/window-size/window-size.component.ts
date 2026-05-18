import { CommonModule } from '@angular/common';
import { Component, HostListener } from '@angular/core';

@Component({
  selector: 'app-window-size',
  imports: [
    CommonModule
  ],
  templateUrl: './window-size.component.html',
  styleUrl: './window-size.component.css'
})
export class WindowSizeComponent {
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
