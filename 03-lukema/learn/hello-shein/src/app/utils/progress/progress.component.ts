import { Component, Input, OnDestroy, OnInit } from '@angular/core';

@Component({
  selector: 'app-progress',
  templateUrl: './progress.component.html',
  styleUrls: ['./progress.component.scss']
})
export class ProgressComponent implements OnInit, OnDestroy {

  constructor() { }

  @Input() submitting = false;
  @Input() timeInSeconds!: number;

  interval$?: NodeJS.Timeout;
  progressValue = 0;
  progressMsg = '';
  progressIterations = 20;

  ngOnInit(): void {
    this.startTimer();
  }

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
