import { CategoryConditions, ItemService } from '@/app/item/item.service';
import { BlockUiService } from '@/app/utils/blockui/blockui.service';
import { ImageUploaderDirective } from '@/app/utils/directives/image-uploader.directive';
import { FileUtilService } from '@/app/utils/services/file-util.service';
import { NmsService } from '@/app/utils/services/nms.service';
import {
  CdkDrag,
  CdkDragDrop,
  CdkDragPlaceholder,
  CdkDropList,
  moveItemInArray,
} from '@angular/cdk/drag-drop';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { HttpErrorResponse, HttpEvent } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NavigationExtras, Router, RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { env } from 'environments/environment';
import {
  BOLD_BUTTON, EditorConfig, INDENT_BUTTON, ITALIC_BUTTON, JUSTIFY_CENTER_BUTTON,
  JUSTIFY_FULL_BUTTON, JUSTIFY_LEFT_BUTTON, JUSTIFY_RIGHT_BUTTON, NgxSimpleTextEditorModule,
  ORDERED_LIST_BUTTON, OUTDENT_BUTTON, REDO_BUTTON, REMOVE_FORMAT_BUTTON, SEPARATOR, STRIKE_THROUGH_BUTTON,
  UNDERLINE_BUTTON, UNDO_BUTTON, UNORDERED_LIST_BUTTON
} from 'ngx-simple-text-editor';
import { Subject, takeUntil } from 'rxjs';
import { DraftService, Item } from './draft.service';
import { MagnifyDialogComponent, MagnifyDialogData } from './magnify-dialog/magnify-dialog.component';
import { SubmittedDialogComponent, SubmittedDialogData } from './submitted-dialog/submitted-dialog.component';


@Component({
  selector: 'app-draft',
  standalone: true,
  imports: [CommonModule, FlexLayoutModule, MatInputModule,
    RouterModule,
    MatButtonModule,
    FormsModule,
    NgxSimpleTextEditorModule,
    MatDividerModule,
    MatRadioModule,
    ReactiveFormsModule,
    CdkDropList,
    CdkDrag,
    CdkDragPlaceholder,
    MatTooltipModule,
    ImageUploaderDirective
  ],
  templateUrl: './draft.component.html',
  styleUrl: './draft.component.scss',
  providers: [
    DraftService,
    ItemService,
    CurrencyPipe
  ]
})
export class DraftComponent implements OnInit, OnDestroy {

  constructor(public router: Router,
    private fileUtilService: FileUtilService,
    private blockUiService: BlockUiService,
    private dialogMagnifier: MatDialog,
    private dialogSubmitted: MatDialog,
    private draftService: DraftService,
    private itemService: ItemService,
    private currencyPipe: CurrencyPipe,
    public nmsService: NmsService
  ) { }

  private readonly destroyed$ = new Subject<void>();

  /** begin required */
  title: string = '';
  category: string = '';
  condition: string = '';
  conditions: string[] = [];
  brand: string = '';
  price: string = '$0';
  /** end required */

  /** begin optional */
  model: string | null | undefined = null;
  californiaProp65Warn: string | null | undefined = null;
  country: string | null | undefined = null;
  availableUnitQuantity: number | null | undefined = 1;
  unitType: string | null | undefined = null;
  mpn: string | null | undefined = null;
  upc: string | null | undefined = null;
  description: string | null | undefined = null;

  id: number | null | undefined = null;

  /** end optional */

  pricePrefix = '$ ';

  imageUrlPrefix = env.imageUrlPrefix;

  photos: string[] = [];

  photoIndexes: number[] = [];

  photoNames: string[] = ['Main Photo', 'Top', 'Bottom', 'Front', 'Back', 'Right side', 'Left side', 'Detail', 'Defect'];

  username: string | null | undefined = '';

  showMoreDetails = false;

  onKeyUpPrice($event: any) {
    const val = $event.target.value;

    let newVal = val.replaceAll(/[^\d.]/g, '');
    const dotIdx = newVal.indexOf('.');
    if (dotIdx > -1) {
      if (newVal.length > dotIdx + 2) {
        newVal = newVal.substring(0, dotIdx + 3);
      }

      const nextDotIdx = newVal.indexOf('.', dotIdx + 1);
      if (nextDotIdx !== -1) {
        newVal = newVal.substring(0, nextDotIdx);
      }
    }

    const num = Number(newVal);

    /**
     * {minIntegerDigits}.{minFractionDigits}-{maxFractionDigits}
     */
    if (dotIdx === -1) {
      newVal = this.currencyPipe.transform(num, 'USD', 'symbol', '1.0');
    } else {
      const decCount = newVal.length - dotIdx - 1;
      if (decCount === 0) {
        newVal = this.currencyPipe.transform(num, 'USD', 'symbol', '1.0') + '.';
      } else if (decCount === 1) {
        newVal = this.currencyPipe.transform(num, 'USD', 'symbol', '1.1-1');
      } else {
        newVal = this.currencyPipe.transform(num, 'USD', 'symbol', '1.2-2');
      }
    }

    this.formGroup.get('price')?.setValue(newVal);

    console.debug('------ newVal:', newVal);
    console.debug('------ newVal num:', newVal.substring(1));
    console.debug('------ newVal num 2:', Number(newVal.replaceAll(/[,]/g, '').substring(1)));

  }

  changeCondition() {
    const cond = this.formGroup.get('condition')?.value;
    if (cond) {
      this.condition = cond;
    }
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.imageFiles, event.previousIndex, event.currentIndex);
    moveItemInArray(this.imageBase64Data, event.previousIndex, event.currentIndex);
  }

  formGroup = new FormGroup({
    /** required */
    title: new FormControl(this.title, [Validators.required]),
    condition: new FormControl(this.condition, [Validators.required]),
    brand: new FormControl(this.brand, [Validators.required]),
    price: new FormControl(this.price, [Validators.required]),
    /** optional */
    model: new FormControl(this.model, []),
    californiaProp65Warn: new FormControl(this.californiaProp65Warn, []),
    country: new FormControl(this.country, []),
    availableUnitQuantity: new FormControl(this.availableUnitQuantity, []),
    unitType: new FormControl(this.unitType, []),
    mpn: new FormControl(this.mpn, []),
    upc: new FormControl(this.upc, []),
  });

  editorConfig: EditorConfig = {
    placeholder: 'Write a detailed description of your item, or save time and let AI draft it for you.',
    buttons: [UNDO_BUTTON,
      REDO_BUTTON,
      REMOVE_FORMAT_BUTTON,
      SEPARATOR,
      BOLD_BUTTON,
      ITALIC_BUTTON,
      UNDERLINE_BUTTON,
      STRIKE_THROUGH_BUTTON,
      SEPARATOR,
      JUSTIFY_LEFT_BUTTON,
      JUSTIFY_CENTER_BUTTON,
      JUSTIFY_RIGHT_BUTTON,
      JUSTIFY_FULL_BUTTON,
      SEPARATOR,
      ORDERED_LIST_BUTTON,
      UNORDERED_LIST_BUTTON,
      SEPARATOR,
      INDENT_BUTTON,
      OUTDENT_BUTTON,
      SEPARATOR]
    // buttons: ST_BUTTONS, /** all the buttons */
  };

  item?: Item;

  ngOnInit() {
    console.debug('DraftComponent ngOnInit() called.');

    this.username = localStorage.getItem('username');

    console.debug('DraftComponent ngOnInit() username:', this.username);

    for (let i = 0; i <= 23; i++) {
      this.photoIndexes.push(i);
    }

    const state = this.router.lastSuccessfulNavigation?.extras.state as any;

    let title: string | null | undefined = '';
    let category: string | null | undefined = '';
    let condition: string | null | undefined = '';

    const id = state?.id;
    if (id) {
      this.itemService.getItem(id).pipe(
        takeUntil(this.destroyed$)
      ).subscribe({
        next: (response: Item) => {
          console.debug('---- DraftComponent getItem response', response);
          this.item = response;

          this.id = this.item?.id;

          title = this.item?.title;
          condition = this.item?.condition;
          category = this.item?.category;

          this.title = title ? title : '';
          this.condition = condition ? condition : '';
          this.category = category ? category : '';

          this.formGroup.get('title')?.setValue(this.title);
          this.formGroup.get('condition')?.setValue(this.condition);

          /**
           * get conditions by category
           */
          this.draftService.getConditionsByCategory(this.category).pipe(
            takeUntil(this.destroyed$)
          ).subscribe({
            next: (response: CategoryConditions) => {
              console.debug('---- DraftComponent getConditionsByCategory response', response);
              this.conditions = response.conditions;
            },
            error: (error: HttpErrorResponse) => {
              console.error('DraftComponent HttpErrorResponse', error);
              if (error.error && error.error.reason) {
                this.errMsg = error.error.reason;
              } else {
                this.errMsg = error.message;
              }
            },
          });

          this.description = this.item.description;

          const brand = this.item.brand;
          let price = this.item.price;
          if (!price) {
            price = 0;
          }

          this.formGroup.get('brand')?.setValue(brand ? brand : '');
          this.formGroup.get('price')?.setValue(this.currencyPipe.transform(price, 'USD', 'symbol', '1.2-2'));

          // const status = this.item.status;

          const availableUnitQuantity = this.item.availableUnitQuantity;

          this.formGroup.get('availableUnitQuantity')?.setValue(availableUnitQuantity);

          /**
           * optional data
           */
          const opt: any = this.item.optionalAttributes;

          const californiaProp65Warn = opt.californiaProp65Warn;
          this.formGroup.get('californiaProp65Warn')?.setValue(californiaProp65Warn ? californiaProp65Warn : '');

          const country = opt.country;
          this.formGroup.get('country')?.setValue(country ? country : '');

          const unitType = opt.unitType;
          this.formGroup.get('unitType')?.setValue(unitType ? unitType : '');

          const mpn = opt.mpn;
          this.formGroup.get('mpn')?.setValue(mpn ? mpn : '');

          const upc = opt.upc;
          this.formGroup.get('upc')?.setValue(upc ? upc : '');

          /**
           * Images
           */
          const fileNames: string[] = this.item.imageFileNames ? this.item.imageFileNames : [];

          fileNames.forEach(fileName => {
            this.draftService.getImage(fileName).pipe(takeUntil(this.destroyed$)).subscribe({
              next: (response: Blob) => {
                this.imageBase64Data.push(URL.createObjectURL(response));
                /**
                 * OK to display image, but no blob data.
                 */
                // this.imageBase64Data.push(env.imageUrlPrefix + '/' + fileName);
                fileName = fileName.substring(20);
                this.imageFiles.push(new File([response], fileName));
              }
            });
          });

        },
        error: (error: HttpErrorResponse) => {
          console.error('DraftComponent HttpErrorResponse', error);
          if (error.error && error.error.reason) {
            this.errMsg = error.error.reason;
          } else {
            this.errMsg = error.message;
          }
        },
      });
    } else {
      title = state?.title;
      condition = state?.condition;
      category = state?.category;

      this.title = title ? title : '';
      this.condition = condition ? condition : '';
      this.category = category ? category : '';

      this.formGroup.get('title')?.setValue(this.title);
      this.formGroup.get('condition')?.setValue(this.condition);

      /**
       * get conditions by category
       */
      this.draftService.getConditionsByCategory(this.category).pipe(
        takeUntil(this.destroyed$)
      ).subscribe({
        next: (response: CategoryConditions) => {
          console.debug('---- DraftComponent getConditionsByCategory response', response);
          this.conditions = response.conditions;
        },
        error: (error: HttpErrorResponse) => {
          console.error('DraftComponent HttpErrorResponse', error);
          if (error.error && error.error.reason) {
            this.errMsg = error.error.reason;
          } else {
            this.errMsg = error.message;
          }
        },
      });
    }
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('DraftComponent ngOnDestroy() called.');

    this.destroyed$.next();
    this.destroyed$.complete();
  }

  gohome() {
    this.router.navigate(['/home']);
  }

  onSubmit() {
    const title = this.formGroup.get('title2')?.value;
    const navigationExtras: NavigationExtras = {
      state: {
        title: title ? title : null
      }
    };

    this.router.navigate(['/sell/findmatch'], navigationExtras);
  }

  onKeyUp() {
    const val = this.formGroup.get('title')?.value;
    this.title = val ? val : '';
  }

  /** images */

  maxSize: number = 1024 * 1024 * 5; /** 5 MB Maximum */

  imageFiles: File[] = [];

  imageBase64Data: string[] = [];

  async fileChanged($event: any): Promise<void> {
    const newFiles: File[] = $event.target?.files;
    this.onDropFiles(newFiles);
  }

  /** drag drop files to upload */
  async onDropFiles(newFiles: File[]): Promise<void> {

    const currImageFileNames = this.imageFiles.map(file => file.name);

    /**
     * Ignore duplicated files.
     */
    Array.from(newFiles).forEach(async item => {
      console.debug('------------ item', item);
      if (!currImageFileNames.includes(item.name)) {
        this.imageFiles.push(item);

        const resultBase64 = await this.fileUtilService.readFileAsDataUrlAsync(item);
        this.imageBase64Data.push(resultBase64);
      }
    });
  }

  async removeFile(idx: number) {
    this.imageFiles.splice(idx, 1);
    this.imageBase64Data.splice(idx, 1);
  }

  selectedIndex: number = 0;

  select(index: number) {
    this.selectedIndex = index;
  }

  magnify(index: number) {
    const matDialogConfig = new MatDialogConfig<MagnifyDialogData>();
    matDialogConfig.panelClass = 'myapp-dialog';
    matDialogConfig.data = { index: index, imageBase64Data: this.imageBase64Data };
    matDialogConfig.disableClose = false;
    matDialogConfig.hasBackdrop = true;
    const dialogRef = this.dialogMagnifier.open<MagnifyDialogComponent, MagnifyDialogData, any>(MagnifyDialogComponent, matDialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialogMagnify was closed');
      this.condition = result;
    });
  }

  saved = false;
  errMsg: string | null | undefined = '';

  submitList(status: string) {

    console.debug('================== submitList called');


    // if (!this.formGroup.valid || this.saved || (this.errMsg && this.errMsg.length > 0)) {
    if (!this.formGroup.valid || (this.errMsg && this.errMsg.length > 0)) {
      console.debug('-------- Draft save() invalid', this.formGroup, this.errMsg);
      return;
    }

    console.debug('-------- Draft save() is valid');

    const formData = new FormData();
    if (this.imageFiles && this.imageFiles.length > 0) {
      for (let i = 0; i < this.imageFiles.length; i++) {
        /**
         * trival
         */
        // (skip) formData.append('files[' + i + ']', this.imageFiles[i], this.imageFiles[i].name);
        /**
         * consize
         */
        formData.append('files', this.imageFiles[i], this.imageFiles[i].name);
      }
    }

    /** required fields */

    const title = this.formGroup.get('title')?.value;
    const brand = this.formGroup.get('brand')?.value;
    let price = this.formGroup.get('price')?.value;
    price = price?.replaceAll(/[,]/g, '').substring(1);

    /** optional fields */
    const model = this.formGroup.get('model')?.value;
    const californiaProp65Warn = this.formGroup.get('californiaProp65Warn')?.value;
    const country = this.formGroup.get('country')?.value;
    const availableUnitQuantityStr = this.formGroup.get('availableUnitQuantity')?.value;
    const unitType = this.formGroup.get('unitType')?.value;
    const mpn = this.formGroup.get('mpn')?.value;
    const upc = this.formGroup.get('upc')?.value;

    /** description is not from formGroup */
    // const description = this.formGroup.get('description')?.value;

    const map: Map<string, any> = new Map();
    map.set('model', model);
    map.set('californiaProp65Warn', californiaProp65Warn);
    map.set('country', country);
    map.set('unitType', unitType);
    map.set('mpn', mpn);
    map.set('upc', upc);

    const jsonMap = Object.fromEntries(map);

    let availableUnitQuantity: number = 1;

    if (!availableUnitQuantityStr) {
      availableUnitQuantity = Number(availableUnitQuantityStr);
    }

    const dateUpdated = Date.now();

    let item: Item = {
      title: title ? title : 'unknown title',
      category: this.category,
      condition: this.condition,
      brand: brand ? brand : 'unknown brand',
      description: this.description,
      price: Number(price),
      optionalAttributes: jsonMap,
      availableUnitQuantity,
      sellerUsername: this.username,
      dateUpdated,
      status: status
    };

    if (this.id) {
      item = {
        id: this.id,
        ...item
      };
    }

    const itemJson = JSON.stringify(item);

    console.debug('--------------- itemJson', itemJson);

    formData.append('itemJson', itemJson);

    this.blockUiService.block();

    this.draftService.doAddItem(formData).pipe(
      takeUntil(this.destroyed$)
    ).subscribe({
      next: (response: HttpEvent<Item>) => {
        this.saved = true;
        this.blockUiService.unblock();

        console.debug('---- saved.', response);

        const body: Item = response['body'] as Item;

        if (body) {
          if (body.id) {
            this.id = body.id;
          }

          this.formGroup.get('title')?.setValue(body.title);
          this.formGroup.get('brand')?.setValue(body.brand);
          const price = body.price;
          this.formGroup.get('price')?.setValue(price ? price.toString() : '$0');

          /** optional fields */
          const optionalAttributes: { [key: string]: any } = body.optionalAttributes ? body.optionalAttributes : {};

          this.formGroup.get('model')?.setValue(optionalAttributes['model']);
          this.formGroup.get('californiaProp65Warn')?.setValue(optionalAttributes['californiaProp65Warn']);
          this.formGroup.get('country')?.setValue(optionalAttributes['country']);
          this.formGroup.get('availableUnitQuantity')?.setValue(optionalAttributes['availableUnitQuantity']);
          this.formGroup.get('unitType')?.setValue(optionalAttributes['unitType']);
          this.formGroup.get('mpn')?.setValue(optionalAttributes['mpn']);
          this.formGroup.get('upc')?.setValue(optionalAttributes['upc']);
        }

        const matDialogConfig = new MatDialogConfig<SubmittedDialogData>();
        matDialogConfig.panelClass = 'myapp-dialog';
        let newStatus = 'listed';
        if (status === 'save') {
          newStatus = 'saved';
        }
        matDialogConfig.data = {
          message: 'Your items has been successfully ' + newStatus +
            '. You can keep editting your item, or go to other pages from menu at the top of the page.'
        };
        matDialogConfig.disableClose = false;
        matDialogConfig.hasBackdrop = true;
        const dialogRef = this.dialogSubmitted.open<SubmittedDialogComponent, SubmittedDialogData, any>(SubmittedDialogComponent, matDialogConfig);

        dialogRef.afterClosed().subscribe(result => {
          console.log('The dialogSaved was closed.', result);
        });
      },
      error: (error: HttpErrorResponse) => {
        console.error('DraftComponent HttpErrorResponse', error);
        this.saved = false;
        this.blockUiService.unblock();
        if (error.error && error.error.reason) {
          this.errMsg = error.error.reason;
        } else {
          this.errMsg = error.message;
        }

        const matDialogConfig = new MatDialogConfig<SubmittedDialogData>();
        matDialogConfig.panelClass = 'myapp-dialog';
        matDialogConfig.data = { message: 'Sorry. We are unable to list your item. Please contact customer service to report this issue. Thanks.' };
        matDialogConfig.disableClose = false;
        matDialogConfig.hasBackdrop = true;
        const dialogRef = this.dialogSubmitted.open<SubmittedDialogComponent, SubmittedDialogData, any>(SubmittedDialogComponent, matDialogConfig);

        dialogRef.afterClosed().subscribe(result => {
          console.log('The dialogSaved was closed.', result);
        });
      },
    });

  }

  listIt() {
    this.submitList('list');
  }

  saveForLater() {
    if (this.id && this.item?.status === 'list') {
      this.submitList('list');
    } else {
      this.submitList('save');
    }
  }

  preview() {

  }
}

