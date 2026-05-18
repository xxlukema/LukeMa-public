import { Product } from '@/app/product/productadd/productadd.service';
import { SelectionModel } from '@angular/cdk/collections';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ProductlistService } from './productlist.service';


@Component({
  selector: 'app-productlist',
  templateUrl: './productlist.component.html',
  styleUrls: ['./productlist.component.scss']
})
export class ProductlistComponent implements OnInit, OnDestroy {

  constructor(private productlistService: ProductlistService,
    public router: Router) {
  }

  channel$?: Subscription;
  loading = false;


  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  listPageSize = 20;

  dataSource = new MatTableDataSource<Product>([]);
  displayedColumns = ['id', 'name', 'description', 'price', 'imageLinks'];
  selection = new SelectionModel<any>(true, []);


  /**
   * Called whenever entering the page/template.
   */
  ngOnInit(): void {
    console.log('ProductlistComponent ngOnInit() called.');
    this.loading = true;

    this.channel$ = this.productlistService.doListProducts().subscribe({
      next: (data) => {
        console.log('ProductlistComponent', data);
        this.loading = false;

        if (data.length === 0) {
          return;
        }

        data.forEach(row => {
          row.imageLinks = [];
          row.images?.forEach(img => {
            row.imageLinks?.push(row.imageUrlPrefix + '/' + img.fileName);
          });
        });

        this.dataSource.data = data;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error: (error: HttpErrorResponse) => {
        console.error('ProductlistComponent', error);
        this.loading = false;
      }

    });
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  page($event: PageEvent) {
    localStorage.setItem('listPageSize', $event.pageSize.toString());
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('ProductlistComponent ngOnDestroy() called.');
    /**
     * Unsbuscribe from Observable channels here.
     */
    if (this.channel$) {
      this.channel$.unsubscribe();
    }
  }

  toDetails(row: any) {
    this.router.navigate(['/productdetail'], {
      queryParams:
      {
        productId: row.id
      }
    });
  }
}
