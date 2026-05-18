import { Component, Input, OnInit } from '@angular/core';

@Component({
  standalone: false,
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss']
})
export class LoadingComponent {

  constructor() { }

  @Input() loading = true;

}
