import { Component, OnDestroy, OnInit } from '@angular/core';

/**
 * Differences between string and String:
 *
 * var s1 = new String("Avoid newing things where possible");
 * var s2 = "A string, in TypeScript of type 'string'";
 * var s3: string;
 *
 * String is the JavaScript String type, which you could use to create new strings. Nobody does this as in JavaScript
 * the literals are considered better. So s2 in the example above creates a new string without the use of the new keyword
 * and without explicitly using the String object.
 *
 * string is the TypeScript string type, which you can use to type variables, parameters and return values.
 */
export interface Car {
  vin: string;
  maker: string;
  year: string;
  color: string | null;
}

@Component({
  selector: 'app-my-primeng-table',
  templateUrl: './my-primeng-table.component.html',
  styleUrls: ['./my-primeng-table.component.scss']
})
export class MyPrimengTableComponent implements OnInit, OnDestroy {

  cols = [
    { field: 'vin' },
    { field: 'year' },
    { field: 'brand' },
    { field: 'color' }
  ];

  cars: Car[] = [
    { vin: '1', maker: 'Toyota', year: '2007', color: 'a' },
    { vin: '4', maker: 'Honda', year: '2017', color: 'A' },
    { vin: '16', maker: 'Acura', year: '2018', color: 'b' },
    { vin: '2', maker: 'Toyota', year: '2011', color: 'B' },
    { vin: '3', maker: 'Ford', year: '1996', color: 'aa' },
    { vin: '5', maker: 'Totoya', year: '2000', color: 'Aa' },
    { vin: '10', maker: 'Volvo', year: '1997', color: 'AA' },
    { vin: '9', maker: 'VW', year: '2007', color: 'aA' },
    { vin: '8', maker: 'Lexus', year: '2007', color: 'BB' },
    { vin: '7', maker: 'GMC', year: '2007', color: '' },
    { vin: '6', maker: 'QQ', year: '2007', color: 'C' },
    { vin: '11', maker: 'Jargua', year: '2007', color: null },

  ];

  rowGroupMetadata: any;

  selectedCars?: Car[];

  onSort(event) {
    console.log('MyPrimengTableComponent onSort() called: ' + event.field + ' ' + event.order);

    if (event.field === 'vin' || event.field === 'year') {
      this.cars.sort((a, b) => event.order * (Number(a[event.field]) - Number(b[event.field])));
    } else {
      this.cars.sort((a, b) => {

        let result = 0;
        if (a[event.field] == null) {
          result = -1;
        } else if (b[event.field] == null) {
          result = 1;
        } else {
          result = a[event.field].localeCompare(b[event.field]);
        }

        return event.order * result;
      }
      );
    }

  }

  clicked(data: any) {
    console.log('clicked:', data);
    if (this.selectedCars) {
      if (this.selectedCars.includes(data)) {
        const idx = this.selectedCars.indexOf(data);
        this.selectedCars.splice(idx, 1);
      } else {
        this.selectedCars.push(data);
      }
    }
  }

  /**
   * Called whenever entering the page/template.
   */
  ngOnInit(): void {
    console.log('MyPrimengTableComponent ngOnInit() called.');
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('MyPrimengTableComponent ngOnDestroy() called.');
    /**
     * Unsbuscribe from Observable channels here.
     */
  }

}
