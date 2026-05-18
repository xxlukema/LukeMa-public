import { Component, OnDestroy, OnInit } from '@angular/core';


@Component({
  selector: 'app-my-mat-progress-bar',
  templateUrl: './my-mat-progress-bar.component.html',
  styleUrls: ['./my-mat-progress-bar.component.scss']
})
export class MyMatProgressBarComponent implements OnInit, OnDestroy {

  color = 'primary';
  mode = 'determinate';
  value = 0;
  intervalId;

  ngOnInit() {

    console.log('MyMatProgressBarComponent ngOnInit()');

    this.intervalId = setInterval(
      () => {
        if (this.value >= 100) {
          clearInterval(this.intervalId);
        } else {
          this.value += 10;
        }
      },
      200
    );
  }

  ngOnDestroy() {
    console.log('MyMatProgressBarComponent ngOnDestroy()');

    clearInterval(this.intervalId);
  }

}
