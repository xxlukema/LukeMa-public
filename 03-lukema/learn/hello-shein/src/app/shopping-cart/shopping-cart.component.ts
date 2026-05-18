import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.scss']
})
export class ShoppingCartComponent implements OnInit {

  constructor() { }

  count = 324;

  len = this.count.toString().length;

  cart = 'Shopping Cart: { ' + this.count + ' }';

  ngOnInit(): void {
    console.log('ShoppingCartComponent ngOnInit() called.');

  }

}
