import { MyObservableService } from '@/app/utils/rxjs/my-observable.service';
import { Component, OnInit } from '@angular/core';

@Component({
  standalone: false,
  selector: 'app-my-observable',
  templateUrl: './my-observable.component.html',
  styleUrls: ['./my-observable.component.scss']
})
export class MyObservableComponent implements OnInit {

  constructor(
    private readonly myObservableService: MyObservableService
  ) { }


  counter = 10;

  ngOnInit(): void {
  }

  add() {
    this.counter++;
    this.myObservableService.publishDataObject(this.counter);
  }

  deduct() {
    this.counter--;
    this.myObservableService.publishDataObject(this.counter);
  }
}
