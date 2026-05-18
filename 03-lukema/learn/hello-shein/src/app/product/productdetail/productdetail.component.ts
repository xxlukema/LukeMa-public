import { NmsService } from '@/app/utils/services/nms.service';
import { CdkTextareaAutosize } from '@angular/cdk/text-field';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Product, ProductdetailService } from './productdetail.service';


@Component({
  selector: 'app-productdetail',
  templateUrl: './productdetail.component.html',
  styleUrls: ['./productdetail.component.scss']
})
export class ProductdetailComponent implements OnInit, OnDestroy {

  constructor(public nmsService: NmsService,
    private productaddService: ProductdetailService,
    public router: Router,
    private route: ActivatedRoute) {
    this.params$ = this.route.queryParams.subscribe((params) => {
      this.productId = params['productId'];
    });
  }

  channel$?: Subscription;
  loading = false;
  saved = false;
  info = '';
  errMsg = '';

  params$: Subscription;

  productId = 0;
  product!: Product;

  maxSize: number = 1024 * 1024 * 50; /** 50 MB Maximum */

  images: File[] = [];

  @ViewChild('autosize') autosize!: CdkTextareaAutosize;

  /**
   * Called whenever entering the page/template.
   */
  ngOnInit(): void {
    console.log('ProductdetailComponent ngOnInit() called.');

    this.loading = true;

    this.channel$ = this.productaddService.doGetProduct(this.productId).subscribe({
      next: (data) => {
        console.log('ProductdetailComponent', data);
        this.loading = false;

        data.imageLinks = [];
        data.images?.forEach(img => {
          data.imageLinks?.push(data.imageUrlPrefix + '/' + img.fileName);
        });

        this.product = data;
      },
      error: (error: HttpErrorResponse) => {
        console.error('ProductlistComponent', error);
        this.loading = false;
      }

    });
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('ProductdetailComponent ngOnDestroy() called.');
    /**
     * Unsbuscribe from Observable channels here.
     */
    if (this.channel$) {
      this.channel$.unsubscribe();
    }
  }

  cancel() {
    // this.router.navigate(['/home/dashboards/device']);
  }

}
