import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ELEMENT_DATA_ARRAY } from './my-mat-table-data-source';
import { LocalStorage, LocalStorageService } from 'ngx-webstorage';
import { Router } from '@angular/router';
import { MyMatTableService } from './my-mat-table.service';

@Component({
  selector: 'app-my-mat-table',
  templateUrl: './my-mat-table.component.html',
  styleUrls: ['./my-mat-table.component.scss'],
})
export class MyMatTableComponent implements OnInit, AfterViewInit {

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  /**
   * https://blog.angular-university.io/angular-material-data-table/
   */
  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol'];

  /**
   * Simpled Table no-sorting no-pagination
   */
  // simpleDataSource = new MatTableDataSource(ELEMENT_DATA_ARRAY.slice(0, 3));  // OK for simple tables data source
  simpleDataSource = ELEMENT_DATA_ARRAY.slice(0, 3); // OK for simple tables data source

  /**
   * For filter and sorting
   */
  // sortableDataSource = new MatTableDataSource(ELEMENT_DATA_ARRAY);
  sortableDataSource = new MatTableDataSource<any>([]);

  /**
   * No filter
   */
  // dataSource = ELEMENT_DATA_ARRAY;

  loading = false;

  constructor(
    private router: Router,
    private myMatTableService: MyMatTableService,
    private localStorageService: LocalStorageService) {
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

    // this.getTicketsInit();
  }

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
  }

  ngAfterViewInit() {
    this.sortableDataSource.paginator = this.paginator;
    this.sortableDataSource.sort = this.sort;

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
    /*
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
