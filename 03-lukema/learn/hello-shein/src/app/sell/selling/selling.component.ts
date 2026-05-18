import { Item } from '@/app/sell/draft/draft.service';
import { CancelConfirmDialogData } from '@/app/utils/dialogs/cancel-confirm-dialog/cancel-confirm-dialog-data';
import { CancelConfirmDialogModule } from '@/app/utils/dialogs/cancel-confirm-dialog/cancel-confirm-dialog.module';
import { CancelConfirmDialogService } from '@/app/utils/dialogs/cancel-confirm-dialog/cancel-confirm-dialog.service';
import { LoadingModule } from '@/app/utils/loading/loading.module';
import { NmsService } from '@/app/utils/services/nms.service';
import { SelectionModel } from '@angular/cdk/collections';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSort, MatSortModule, Sort } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NavigationExtras, Router, RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { env } from 'environments/environment';
import { Subject, takeUntil } from 'rxjs';
import { SellingService } from './selling.service';



@Component({
  selector: 'app-selling',
  standalone: true,
  imports: [CommonModule, FlexLayoutModule, MatInputModule,
    RouterModule,
    MatButtonModule,
    MatTableModule, MatPaginatorModule, MatSortModule,
    MatCardModule,
    LoadingModule,
    CancelConfirmDialogModule,
    MatTooltipModule
  ],
  providers: [
    SellingService,
    CancelConfirmDialogService
  ],
  templateUrl: './selling.component.html',
  styleUrl: './selling.component.scss'
})
export class SellingComponent implements OnInit, OnDestroy, AfterViewInit {

  constructor(public router: Router,
    private sellingService: SellingService,
    public nmsService: NmsService,
    private cancelConfirmDialogService: CancelConfirmDialogService
  ) { }

  private readonly destroyed$ = new Subject<void>();

  username: string | null | undefined = '';

  imageUrlPrefix = env.imageUrlPrefix;

  items: Item[] = [];
  errMsg = '';

  loading = false;

  pageSize: number = 20;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  sortableDataSourceSimpler = new MatTableDataSource<Item>([]);

  selection = new SelectionModel<Item>(true, []);

  page($event: PageEvent) {
    localStorage.setItem(this.pageSizeName, $event.pageSize.toString());
  }

  toggleRow(row: Item) {
    const isSelected = this.selection.isSelected(row);
    this.selection.clear();
    if (isSelected) {
      this.selection.clear();
    } else {
      this.selection.toggle(row);
    }
  }

  sortAsc = true;
  pageIndex = 0;

  pageSizeName = 'pageSize';

  /**
   * Not in use
   */
  public sortDataSimpler($sort: Sort) {
    console.log('============ $sort simpler', $sort);
    if ($sort.direction === '') {
      return;
    }

    let isAsc = true;
    if ($sort.direction === 'asc') {
      isAsc = true;
    } else {
      isAsc = false;
    }

    this.sortableDataSourceSimpler.data.sort((a: Item, b: Item) => {
      switch ($sort.active) {
        case 'title':
          return this.compare(a.title, b.title, isAsc);
        case 'condition':
          return this.compare(a.condition, b.condition, isAsc);
        case 'availableUnitQuantity':
          return this.compare(a.availableUnitQuantity, b.availableUnitQuantity, isAsc);
        case 'price':
          return this.compare(a.price, b.price, isAsc);
        default:
          return 0;
      }
    });
  }

  compare = (a: number | null | undefined | string, b: number | undefined | null | string, isAsc: boolean) => {
    if (a && b) {
      return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
    } else {
      if (!a && !b) {
        return 0;
      } else if (a) {
        return 1 * (isAsc ? 1 : -1);
      } else {
        return -1 * (isAsc ? 1 : -1);
      }
    }
  };


  /**
   * https://blog.angular-university.io/angular-material-data-table/
   */
  displayedColumns: string[] = ['imageFileNames', 'title', 'category', 'condition', 'availableUnitQuantity', 'dateUpdated', 'price', 'status', 'edit', 'list', 'unlist', 'delete'];

  ngOnInit() {
    console.log('SellingComponent ngOnInit() called.');
    /*
    this.eventService.emitEvent({
      isInSellingPage: true
    });
    */

    const psize = localStorage.getItem(this.pageSizeName);
    if (psize) {
      this.pageSize = Number(psize);
    }

    this.username = localStorage.getItem('username');
    const uname = this.username ? this.username : '';

    console.log('SellingComponent ngOnInit() uname:', uname);

    if (this.username) {
      this.loading = true;
      this.sellingService.getItemsForSeller(uname).pipe(
        takeUntil(this.destroyed$)
      ).subscribe({
        next: (response) => {
          this.loading = false;
          console.debug('---- SellingComponent response', response);
          // this.sortableDataSourceSimpler = new MatTableDataSource<Item>(response);
          this.sortableDataSourceSimpler.data = response;
        },
        error: (error: HttpErrorResponse) => {
          this.loading = false;
          console.error('SellingComponent HttpErrorResponse', error);
          if (error.error && error.error.reason) {
            this.errMsg = error.error.reason;
          } else {
            this.errMsg = error.message;
          }
        },
      });
    }
  }

  ngAfterViewInit() {
    this.sortableDataSourceSimpler.paginator = this.paginator;
    this.sortableDataSourceSimpler.sort = this.sort;

    /**
     * Setting default sorting column:
     * https://www.htmlgoodies.com/javascript/custom-sort-javascript-tables/
     */
    /*
    const sortState: Sort = {
      active: 'name',
      direction: 'desc',
      // direction: 'asc'
    };
    this.sort.active = sortState.active;
    this.sort.direction = sortState.direction;
    this.sort.sortChange.emit(sortState);
    */


  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('SellingComponent ngOnDestroy() called.');

    this.destroyed$.next();
    this.destroyed$.complete();
  }

  scrollTo($element: any): void {
    $element.scrollIntoView({ behavior: 'smooth', block: 'start', inline: 'nearest' });
  }

  gohome() {
    this.router.navigate(['/home']);
  }

  gotoItem(id: number | null | undefined) {
    if (!id) {
      return;
    }

    this.router.navigate(['/item'], { state: { id } });
  }

  gotoListing() {
    console.log('---------goto listing.');
    this.router.navigate(['/sell/list']);
  }

  edit(row: Item) {
    const navigationExtras: NavigationExtras = {
      state: {
        id: row.id
      } as any
    };

    this.router.navigate(['/sell/draft'], navigationExtras);
  }

  list(row: Item) {
    const cancelConfirmDialogData: CancelConfirmDialogData = {
      title: 'Confirm List Item',
      content: 'List this item?',
      cancelButtonLabel: 'Cancel',
      confirmButtonLabel: 'Confirm'
    };
    this.cancelConfirmDialogService.open(cancelConfirmDialogData);

    this.cancelConfirmDialogService.confirmed().subscribe(
      result => {
        console.debug('----------------- result:', result);

        if (result && result === 'confirm') {
          const id = row.id ? row.id : -1;
          this.sellingService.listItem(id).pipe(
            takeUntil(this.destroyed$)
          ).subscribe({
            next: (response: Item) => {
              this.loading = false;
              console.debug('---- SellingComponent list response', response);
              this.ngOnInit();
            },
            error: (error: HttpErrorResponse) => {
              this.loading = false;
              console.error('SellingComponent HttpErrorResponse', error);
              if (error.error && error.error.reason) {
                this.errMsg = error.error.reason;
              } else {
                this.errMsg = error.message;
              }
            },
          });
        }
      }
    );
  }

  unlist(row: Item) {
    const cancelConfirmDialogData: CancelConfirmDialogData = {
      title: 'Confirm Unlist Item',
      content: 'Unlist this item? This will make the item unsearchable to buyers.',
      cancelButtonLabel: 'Cancel',
      confirmButtonLabel: 'Confirm'
    };
    this.cancelConfirmDialogService.open(cancelConfirmDialogData, 14, 30);

    this.cancelConfirmDialogService.confirmed().subscribe(
      result => {
        console.debug('----------------- result:', result);

        if (result && result === 'confirm') {
          const id = row.id ? row.id : -1;
          this.sellingService.unListItem(id).pipe(
            takeUntil(this.destroyed$)
          ).subscribe({
            next: (response: Item) => {
              this.loading = false;
              console.debug('---- SellingComponent unlist response', response);
              this.ngOnInit();
            },
            error: (error: HttpErrorResponse) => {
              this.loading = false;
              console.error('SellingComponent HttpErrorResponse', error);
              if (error.error && error.error.reason) {
                this.errMsg = error.error.reason;
              } else {
                this.errMsg = error.message;
              }
            },
          });
        }
      }
    );
  }

  delete(row: Item) {
    const cancelConfirmDialogData: CancelConfirmDialogData = {
      title: 'Confirm Delete Item',
      content: 'Delete this item?',
      cancelButtonLabel: 'Cancel',
      confirmButtonLabel: 'Confirm'
    };
    this.cancelConfirmDialogService.open(cancelConfirmDialogData, 13, 30);

    this.cancelConfirmDialogService.confirmed().subscribe(
      result => {
        console.debug('----------------- result:', result);

        if (result && result === 'confirm') {
          const id = row.id ? row.id : -1;
          this.sellingService.deleteItem(id).pipe(
            takeUntil(this.destroyed$)
          ).subscribe({
            next: (response: Item) => {
              this.loading = false;
              console.debug('---- SellingComponent delete response', response);
              this.ngOnInit();
            },
            error: (error: HttpErrorResponse) => {
              this.loading = false;
              console.error('SellingComponent HttpErrorResponse', error);
              if (error.error && error.error.reason) {
                this.errMsg = error.error.reason;
              } else {
                this.errMsg = error.message;
              }
            },
          });
        }
      }
    );
  }


}
