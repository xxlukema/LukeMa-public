import { BlockUiService } from '@/app/utils/blockui/blockui.service';
import { FileUtilService } from '@/app/utils/services/file-util.service';
import { NmsService } from '@/app/utils/services/nms.service';
import { CdkTextareaAutosize } from '@angular/cdk/text-field';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subject, Subscription, takeUntil } from 'rxjs';
import { Product, ProductaddService } from './productadd.service';


@Component({
  selector: 'app-productadd',
  templateUrl: './productadd.component.html',
  styleUrls: ['./productadd.component.scss']
})
export class ProductaddComponent implements OnInit, OnDestroy {

  constructor(public nmsService: NmsService,
    private productaddService: ProductaddService,
    private fileUtilService: FileUtilService,
    private blockUiService: BlockUiService,) { }

  private readonly destroyed$ = new Subject<void>();

  loading = false;
  saved = false;
  info = '';
  errMsg = '';

  maxSize: number = 1024 * 1024 * 50; /** 50 MB Maximum */

  imageFiles: File[] = [];

  @ViewChild('autosize') autosize!: CdkTextareaAutosize;

  /**
   * Called whenever entering the page/template.
   */
  ngOnInit(): void {
    console.log('ProductaddComponent ngOnInit() called.');
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('ProductaddComponent ngOnDestroy() called.');
    /**
     * Unsbuscribe from Observable channels here.
     */
    this.destroyed$.next();
    this.destroyed$.unsubscribe();
  }

  formGroup = new FormGroup({
    name: new FormControl('', Validators.required),
    description: new FormControl(''),
    price: new FormControl('', Validators.required),
  });

  imageBase64Data: string[] = [];

  async fileChanged($event: any) {
    const newFiles = $event.target.files;
    for (let f = 0; f < newFiles.length; f++) {
      let dupe = false;
      for (let i = 0; i < this.imageFiles.length; i++) {
        if (newFiles[f].name === this.imageFiles[i].name) {
          dupe = true;
          break;
        }
      }

      if (!dupe && this.imageFiles.length < this.maxSize) {
        this.imageFiles.push(newFiles[f]);
      }
    }

    // re-draw images
    this.imageBase64Data = [];
    for (let i = 0; i < this.imageFiles.length; i++) {
      const resultBase64 = await this.fileUtilService.readFileAsDataUrlAsync(this.imageFiles[i]);
      this.imageBase64Data.push(resultBase64);
    }
  }

  async removeFile(idx: number) {
    this.imageFiles.splice(idx, 1);

    // re-draw images
    this.imageBase64Data = [];
    for (let i = 0; i < this.imageFiles.length; i++) {
      const resultBase64 = await this.fileUtilService.readFileAsDataUrlAsync(this.imageFiles[i]);
      this.imageBase64Data.push(resultBase64);
    }
  }

  getFileIcon(name: string) {
    return this.fileUtilService.getFileIcon(name);
  }

  change() {
    this.saved = false;
    this.errMsg = '';
  }

  cancel() {
    // this.router.navigate(['/home/dashboards/device']);
  }

  reset() {
    this.formGroup.reset();
    this.errMsg = '';
    this.info = '';
    this.saved = false;
    this.loading = false;
  }

  save() {
    if (!this.formGroup.valid || this.saved || this.errMsg.length > 0) {
      return;
    }

    const name = this.formGroup.get('name')?.value;
    const description = this.formGroup.get('description')?.value;
    const price = this.formGroup.get('price')?.value;

    const product: Product = {
      name: name ? name : '',
      description: description,
      price: price ? Number(price) : 0
    };

    const formData = new FormData();
    if (this.imageFiles && this.imageFiles.length > 0) {
      for (let i = 0; i < this.imageFiles.length; i++) {
        // formData.append('files[' + i + ']', this.imageFiles[i], this.imageFiles[i].name);
        formData.append('files', this.imageFiles[i], this.imageFiles[i].name);
      }
    }

    formData.append('prodJson', JSON.stringify(product));

    this.blockUiService.block();

    this.productaddService.doAddProduct(formData).pipe(
      takeUntil(this.destroyed$)
    ).subscribe({
      next: (response) => {
        this.saved = true;
        this.blockUiService.unblock();
        console.debug('---- saved', response);
      },
      error: (error: HttpErrorResponse) => {
        console.error('ProductAddComponent HttpErrorResponse', error);
        this.saved = false;
        this.blockUiService.unblock();
        if (error.error && error.error.reason) {
          this.errMsg = error.error.reason;
        } else {
          this.errMsg = error.message;
        }
      },
    });
  }

  localUrl: string | null | undefined;

  clearImages() {
    this.imageFiles = [];
    this.localUrl = null;
  }

  uploadImages($event: any) {
    if ($event.target.files && $event.target.files[0]) {
      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.localUrl = event.target.result;
      };
      reader.readAsDataURL($event.target.files[0]);
      this.imageFiles = $event.target.files[0].name;
    }
  }

}
