import {Component} from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';


@Component({
  templateUrl: './rent.component.html'
})
export class RentComponent {
  email = 'x.luke.ma@gmail.com';
  phone = '832-588-7811';

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  allListings() {
    this.router.navigate(['/addRentProperty']);
  }

  addRentProperty() {
    this.router.navigate(['/addRentProperty']);
  }

  roommateWanted() {
    this.router.navigate(['/addRentProperty']);
  }
}
