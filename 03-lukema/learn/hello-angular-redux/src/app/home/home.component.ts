import { MyResolveService } from '@/app/utils/my-resolve/my-resolve.service';
import { Component, OnDestroy, OnInit } from '@angular/core';

@Component({
  standalone: false,
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {

  constructor(private readonly myResolveService: MyResolveService) { }

  /**
   * Called whenever entering the page/template.
   */
  ngOnInit(): void {
    console.log('HomeComponent ngOnInit() called.');
    this.myResolveService.setFrom('Home Page');
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
