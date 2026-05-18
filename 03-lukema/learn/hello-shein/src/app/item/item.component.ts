import { Item } from '@/app/sell/draft/draft.service';
import { CommonModule, DecimalPipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { NavigationExtras, Router, RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { env } from 'environments/environment';
import { Subject, takeUntil } from 'rxjs';
import { LoadingModule } from '../utils/loading/loading.module';
import { NmsService } from '../utils/services/nms.service';
import { ItemService } from './item.service';



@Component({
  selector: 'app-item',
  standalone: true,
  imports: [CommonModule, FlexLayoutModule, MatInputModule,
    RouterModule,
    LoadingModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule
  ],
  providers: [
    ItemService,
    DecimalPipe
  ],
  templateUrl: './item.component.html',
  styleUrl: './item.component.scss'
})
export class ItemComponent implements OnInit, OnDestroy {

  constructor(private router: Router,
    private itemService: ItemService,
    private decimalPipe: DecimalPipe,
    public nmsService: NmsService
  ) { }

  private readonly destroyed$ = new Subject<void>();

  @ViewChild('panel') public panel!: ElementRef<any>;

  loading = false;

  selectedIdx = 0;

  imageUrlPrefix = env.imageUrlPrefix;

  quantity: number = 1;

  price: number = 0;

  discount: number = 0;

  formGroup = new FormGroup({
    /** required */
    quantity: new FormControl(this.quantity, [Validators.required])
  });

  get bulkDiscount(): number {
    if (this.discount === 0) {
      return 0;
    }

    let quan = this.formGroup.get('quantity')?.value;
    if (!quan) {
      quan = 1;
    }

    switch (quan) {
      case 1:
        return 0;
      case 2:
        return this.discount;
      case 3:
        return this.discount * 2;
      default:
        return this.discount * 3;
    }
  }

  buy(quan: number) {
    if (this.item.availableUnitQuantity && this.item.availableUnitQuantity >= quan) {
      this.formGroup.get('quantity')?.setValue(quan);
      this.quantity = quan;
    }
  }

  onSubmit() {
  }

  buyItNow() {
    const navigationExtras: NavigationExtras = {
      state: {
        item: this.item,
        quantity: this.quantity
      } as any
    };

    console.debug('------ to buyitnow extras:', navigationExtras);

    this.router.navigate(['/sell/buyitnow'], navigationExtras);
  }

  addToCart() {

  }

  addToWishList() {
  }

  onKeyUpNumber($event) {
    const val = $event.target.value;

    let newVal = val.replaceAll(/[^\d]/g, '');

    if (newVal.length == 0) {
      return;
    }

    const num = Number(newVal);
    this.quantity = num;

    /**
     * {minIntegerDigits}.{minFractionDigits}-{maxFractionDigits}
     */
    newVal = this.decimalPipe.transform(num, '1.0-0');

    this.formGroup.get('quantity')?.setValue(newVal);

    console.debug('------ newVal:', newVal);
    console.debug('------ newVal num:', newVal.substring(1));
    console.debug('------ newVal num 2:', Number(newVal.replaceAll(/[,]/g, '').substring(1)));
  }

  selected($index: number) {
    this.selectedIdx = $index;
  }

  increase() {
    const len = this.item.imageFileNames?.length;
    if (len) {
      if (this.selectedIdx < len - 1) {
        this.selectedIdx++;
        this.panel.nativeElement.scrollTop += 20;
      }
    }
  }

  decrease() {
    if (this.selectedIdx > 0) {
      this.selectedIdx--;
      this.panel.nativeElement.scrollTop -= 20;
    }
  }

  item!: Item;

  errMsg = '';

  ngOnInit() {
    console.log('ItemComponent ngOnInit() called.');
    const state = this.router.lastSuccessfulNavigation?.extras.state as {
      id: number
    };

    const id = state.id;

    this.loading = true;

    this.itemService.getItem(id).pipe(takeUntil(this.destroyed$)).subscribe({
      next: (response) => {
        this.loading = false;
        this.item = response;
        this.price = this.item.price ? this.item.price : 0;
        this.discount = this.item.discount ? this.item.discount : 0;
        console.debug('---- ItemComponent response', response);
      },
      error: (error: HttpErrorResponse) => {
        this.loading = false;
        console.error('ItemComponent HttpErrorResponse', error);
        if (error.error && error.error.reason) {
          this.errMsg = error.error.reason;
        } else {
          this.errMsg = error.message;
        }
      },
    });


  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('SellComponent ngOnDestroy() called.');

    this.destroyed$.next();
    this.destroyed$.complete();
  }

  gohome() {
    this.router.navigate(['/home']);
  }

}
