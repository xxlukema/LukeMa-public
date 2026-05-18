import { Component, Inject, Input, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { BlockUiData } from './blockui.data';

@Component({
  selector: 'app-blockui',
  templateUrl: './blockui.component.html',
  styleUrls: ['./blockui.component.scss']
})
export class BlockUiComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public blockUiData: BlockUiData) {
  }

  @Input() updating = true;

  ngOnInit() {
    if (this.blockUiData) {
      this.timeInSeconds = this.blockUiData.timeInSeconds;
      this.displayTime = this.blockUiData.displayTime;
      this.msg = this.blockUiData.msg;
      if (this.timeInSeconds) {
        this.startTimer();
      }
    }
  }

  timeInSeconds!: number;
  msg?: string;
  displayTime?: boolean = true;

  interval$?: NodeJS.Timeout;
  progressValue = 0;
  progressMsg = '';
  progressIterations = 20;

  ngOnDestroy(): void {
    if (this.interval$) {
      clearInterval(this.interval$);
    }
  }

  updateProgressMsg() {
    if (this.progressValue >= 100) {
      this.progressValue = 100;
      this.progressMsg = Math.round(this.progressValue) + '% Completing...';
      if (this.interval$) {
        clearInterval(this.interval$);
      }
    } else {
      this.progressMsg = Math.round(this.progressValue) + '%';
    }
  }

  startTimer() {
    this.progressValue = 0;
    this.progressMsg = '';
    const interval = this.timeInSeconds * (1_000 / this.progressIterations);
    const increment = 100 / this.progressIterations;

    this.updateProgressMsg();
    this.interval$ = setInterval(() => {
      if (this.progressValue >= 100) {
        if (this.interval$) {
          clearInterval(this.interval$);
        }
      } else {
        this.progressValue += increment;
        this.updateProgressMsg();
      }
    }, interval);
  }
}
