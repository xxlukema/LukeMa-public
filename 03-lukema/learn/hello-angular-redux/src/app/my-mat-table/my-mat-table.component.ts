import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { LocalStorage, LocalStorageService } from 'ngx-webstorage';
import { ELEMENT_DATA_ARRAY, PeriodicElement } from './my-mat-table-data-source';
import { MyMatTableService } from './my-mat-table.service';

@Component({
  standalone: false,
  selector: 'app-my-mat-table',
  templateUrl: './my-mat-table.component.html',
  styleUrls: ['./my-mat-table.component.scss'],
})
export class MyMatTableComponent implements OnInit, AfterViewInit {

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  @ViewChild('simplerTable', { read: MatSort, static: false }) sortSimpler!: MatSort;

  /**
   * https://blog.angular-university.io/angular-material-data-table/
   */
  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol'];

  /**
   * Simpled Table no-sorting no-pagination
   */
  simpleDataSource = ELEMENT_DATA_ARRAY.slice(0, 3); // OK for simple tables data source

  /**
   * For filter and sorting
   */
  sortableDataSource = new MatTableDataSource<any>([]);

  sortableDataSourceSimpler = new MatTableDataSource<PeriodicElement>([]);

  /**
   * No filter
   */
  loading = false;

  constructor(
    private readonly router: Router,
    private readonly myMatTableService: MyMatTableService,
    private readonly localStorageService: LocalStorageService) {
    // http%3A//localhost%3A4200/%23/home
    // window.location.href = 'http://localhost:8083/auth/realms/nms/protocol/openid-connect/auth?client_id=npm-client';
    // window.location.href = 'http://localhost:8083/auth/realms/nms/account/#/';
    // window.location.href = 'http://localhost:8083/auth/realms/nms/protocol/openid-connect/auth?redirect_uri=http%3A%2F%2Flocalhost%3A4200%2F&client_id=account-console&state=c65b76b1-23a9-4aef-b40f-37db0bd1e829&response_mode=fragment&response_type=code&scope=openid&nonce=bfc791f9-ac36-4500-8f16-90f19549e4cf&code_challenge=_UNszypjhvwoSZS0IKnNdZ5HeTtYumAgH7QO--3Cvo8&code_challenge_method=S256';
  }

  @LocalStorage('myPageSize', 10) myPageSize!: number;

  page($event: PageEvent) {
    this.localStorageService.store('myPageSize', $event.pageSize);
  }

  sortProperty = 'position';
  sortAsc = true;
  pageIndex = 0;

  /**
   * This is called before this.sortableDataSource.sortingDataAccessor() is called. For server side sorting, load sorted server data here.
   * https://www.htmlgoodies.com/javascript/custom-sort-javascript-tables/
   */
  public sortData($sort: Sort) {
    console.log('============ $sort', $sort);
    if ($sort.direction === '') {
      return;
    }
    this.sortProperty = $sort.active;
    if ($sort.direction === 'asc') {
      this.sortAsc = true;
    } else {
      this.sortAsc = false;
    }
    this.pageIndex = 0;
  }

  /**
   * <table #simplerTable mat-table [dataSource]="sortableDataSourceSimpler" matSort (matSortChange)="sortDataSimpler($event)" class="mat-elevation-z8">
   */
  public sortDataSimpler($sort: Sort) {
    console.log('============ $sort simpler', $sort);
    if ($sort.direction === '') {
      return;
    }
    this.sortProperty = $sort.active;
    let isAsc = true;
    if ($sort.direction === 'asc') {
      isAsc = true;
    } else {
      isAsc = false;
    }

    this.sortableDataSourceSimpler.data.sort((a, b) => {
      switch ($sort.active) {
        case 'position':
          return this.compare(a.position, b.position, isAsc);
        case 'name':
          return this.compare(a.name, b.name, isAsc);
        case 'weight':
          return this.compare(a.weight, b.weight, isAsc);
        case 'symbol':
          return this.compare(a.symbol, b.symbol, isAsc);
        default:
          return 0;
      }
    });
  }

  compare = (a: number | string, b: number | string, isAsc: boolean) => {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  };

  ngOnInit(): void {
    this.loading = true;
    this.myMatTableService.getTableData().subscribe({
      next: (response: any[]) => {
        this.loading = false;
        console.log('MyMatTableComponent', response);
        this.sortableDataSource.data = response;
        this.sortableDataSource.sort = this.sort;
        this.sortableDataSource.paginator = this.paginator;
      },
      error: error => {
        this.loading = false;
        console.error('MyMatTableComponent', error);
      }
    });
    this.myMatTableService.getTableDataSimpler().subscribe({
      next: (response: any[]) => {
        this.loading = false;
        console.log('MyMatTableComponent simpler', response);
        this.sortableDataSourceSimpler.data = response;
        this.sortableDataSourceSimpler.sort = this.sortSimpler;
      },
      error: error => {
        this.loading = false;
        console.error('MyMatTableComponent simpler', error);
      }
    });
  }

  ngAfterViewInit() {
    this.sortableDataSource.paginator = this.paginator;
    this.sortableDataSource.sort = this.sort;

    this.sortableDataSourceSimpler.sort = this.sortSimpler;
    /**
    // this.sortSimpler.disableClear = true;
    // this.sortSimpler.sort({ disableClear: true, id: this.displayedColumns[1], start: 'asc' });
    */

    /**
     * Setting default sorting column:
     * https://www.htmlgoodies.com/javascript/custom-sort-javascript-tables/
     */
    const sortState: Sort = {
      active: 'name',
      direction: 'desc',
      // direction: 'asc'
    };
    this.sort.active = sortState.active;
    this.sort.direction = sortState.direction;
    this.sort.sortChange.emit(sortState);

    /**
     * Customize sorting:
     * https://www.htmlgoodies.com/javascript/custom-sort-javascript-tables/
     */
    /**
     this.sortableDataSource.sortingDataAccessor = (item, property) => {
      switch (property) {
        case 'date': {
          return new Date(item['date']);
        }
        default: {
          return item[property];
        }
      }
    };
    */
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.sortableDataSource.filter = filterValue.trim().toLowerCase();

    if (this.sortableDataSource.paginator) {
      this.sortableDataSource.paginator.firstPage();
    }
  }
}
