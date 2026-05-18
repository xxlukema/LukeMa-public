import { Component, OnInit, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {

  /**
   * Called whenever entering the page/template.
   */
  ngOnInit(): void {
    console.log('HomeComponent ngOnInit() called.');
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('HomeComponent ngOnDestroy() called.');
    /**
     * Unsbuscribe from Observable channels here.
     */
  }

}
