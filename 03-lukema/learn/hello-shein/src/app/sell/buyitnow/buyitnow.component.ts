import { ItemService } from '@/app/item/item.service';
import { Item } from '@/app/sell/draft/draft.service';
import { LoadingModule } from '@/app/utils/loading/loading.module';
import { NmsService } from '@/app/utils/services/nms.service';
import { CommonModule, DecimalPipe } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Router, RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { env } from 'environments/environment';
import { Subject } from 'rxjs';


@Component({
  selector: 'app-item',
  standalone: true,
  imports: [CommonModule, FlexLayoutModule, MatInputModule,
    RouterModule,
    LoadingModule,
    FormsModule,
    MatRadioModule,
    MatTooltipModule,
    ReactiveFormsModule,
    MatButtonModule
  ],
  providers: [
    ItemService,
    DecimalPipe
  ],
  templateUrl: './buyitnow.component.html',
  styleUrl: './buyitnow.component.scss'
})
export class BuyitnowComponent implements OnInit, OnDestroy {

  constructor(public router: Router,
    public nmsService: NmsService,
    private decimalPipe: DecimalPipe) { }

  private readonly destroyed$ = new Subject<void>();

  loading = false;

  item!: Item;

  quantity!: number;

  discount = 0;

  imageUrlPrefix = env.imageUrlPrefix;

  imageFileName = '';

  totalPrice = 0;

  selectedPayWith: string = 'saved';

  shippingCost = 0;

  freeDeliveryDateFrom = new Date();
  freeDeliveryDateTo = new Date();

  fastDeliveryDateFrom = new Date();
  fastDeliveryDateTo = new Date();

  /**
   * TODO: fix tax rate
   */
  taxRate = 0.06;

  formGroup = new FormGroup({
    /** required */
    quantity: new FormControl(this.quantity, [Validators.required]),
    selectedPayWith: new FormControl(this.selectedPayWith, [Validators.required]),
    shippingCost: new FormControl(this.shippingCost, [])
  });

  confirmAndBuy() {

  }

  ngOnInit(): void {
    const state = this.router.lastSuccessfulNavigation?.extras.state as any;
    this.item = state.item;
    this.quantity = state.quantity;
    this.imageFileName = this.item.imageFileNames ? this.item.imageFileNames[0] : '';
    this.discount = this.item.discount ? this.item.discount : 0;

    this.formGroup.get('quantity')?.setValue(this.quantity);

    this.totalPrice = (this.item.price ? this.item.price : 0) * this.quantity * (1 - this.bulkDiscount);

    this.formGroup.get('shippingCost')?.setValue(this.shippingCost);

    const now = new Date();

    this.freeDeliveryDateFrom.setDate(now.getDate() + 4);
    this.freeDeliveryDateTo.setDate(this.freeDeliveryDateFrom.getDate() + 3);

    this.fastDeliveryDateFrom.setDate(now.getDate() + 2);
    this.fastDeliveryDateTo.setDate(this.fastDeliveryDateFrom.getDate() + 3);
  }

  onSubmit() {
  }

  update() {
    let quan = this.formGroup.get('quantity')?.value;
    if (!quan) {
      quan = 1;
    }

    this.quantity = quan;

    this.totalPrice = (this.item.price ? this.item.price : 0) * this.quantity * (1 - this.bulkDiscount);
  }

  get bulkDiscount(): number {
    if (this.discount === 0) {
      return 0;
    }

    let quan = this.formGroup.get('quantity')?.value;
    if (!quan) {
      quan = 1;
    }

    this.quantity = quan;

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

  shippingChange($event) {
    console.debug('---- changed:', $event.value);

    this.formGroup.get('shippingCost')?.setValue($event.value);
    this.shippingCost = $event.value;
  }

  payMethodChange($event) {
    console.debug('---- changed:', $event.value);

    this.formGroup.get('selectedPayWith')?.setValue($event.value);
    this.selectedPayWith = $event.value;
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
