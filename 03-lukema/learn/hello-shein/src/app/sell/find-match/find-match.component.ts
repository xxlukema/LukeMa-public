import { CategoryConditions } from '@/app/item/item.service';
import { LoadingModule } from '@/app/utils/loading/loading.module';
import { NmsService } from '@/app/utils/services/nms.service';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { NavigationExtras, Router, RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { Subject, lastValueFrom, takeUntil } from 'rxjs';
import { ConditionDialogComponent, ConditionDialogData } from './condition-dialog/condition-dialog.component';
import { FindMatchService } from './find-match.service';

@Component({
  selector: 'app-find-match',
  standalone: true,
  imports: [CommonModule, FlexLayoutModule, MatInputModule,
    RouterModule,
    MatButtonModule,
    FormsModule,
    LoadingModule,
    ReactiveFormsModule
  ],
  providers: [
    FindMatchService
  ],
  templateUrl: './find-match.component.html',
  styleUrl: './find-match.component.scss'
})
export class FindMatchComponent implements OnInit, OnDestroy {

  constructor(public router: Router,
    private readonly matDialog: MatDialog,
    private readonly findMatchService: FindMatchService,
    public nmsService: NmsService
    // private eventService: EventService
  ) {
    /**
     * !!! Trick !!!
     * this.router.getCurrentNavigation() must be used inside constructor. Otherwise, it will always be always null;
     */
    /**
    const state = this.router.getCurrentNavigation()?.extras.state as {
      title: string
    };

    this.title = state?.title;
    */
  }

  private readonly destroyed$ = new Subject<void>();

  loading = false;

  title = '';

  category = '';
  categoryConditions: CategoryConditions[] = [];

  errMsg = '';

  formGroup = new FormGroup({
    category: new FormControl(this.category, Validators.required),
  });


  ngOnInit() {
    console.log('FindMatchComponent ngOnInit() called.');
    /**
    this.eventService.emitEvent({
    });
    */

    /**
     * !!! Trick !!!
     * this.router.getCurrentNavigation() must be used inside constructor. Otherwise, it will always be always null;
     *
     * Step 1. retrieve title of the listing.
     */
    const state = this.router.lastSuccessfulNavigation?.extras.state as {
      title: string
    };

    this.title = state?.title;

    /**
     * step 2. get all categories if user wants to change category (category determines available conditions).
     */
    this.loading = true;
    lastValueFrom(this.findMatchService.getAllCategories().pipe(
      takeUntil(this.destroyed$)
    )).then(
      (response: any) => {
        console.debug('FindMatchComponent getAllCategories() --- step 1.', response);
        this.loading = false;
        this.categoryConditions = response;

        console.debug('FindMatchComponent getAllCategories() --- step 2.');

        /**
         * step 3. guess category
         */
        this.loading = true;
        lastValueFrom(this.findMatchService.getCategoryConditions(this.title).pipe(
          takeUntil(this.destroyed$)
        )).then(
          (response: CategoryConditions) => {
            console.debug('FindMatchComponent getCategoryConditions() --- step 3.', response);
            this.loading = false;
            this.category = response.category;
            /**
             * !!! Trick !!!
             * Default dropdown value is set here!
             *
             * step 4. update category of input, so that the categories dropdown default option matches this category.
             */
            this.formGroup.get('category')?.setValue(this.category);  // <============== Set default here!

            /**
             * step 4. set conditions based on category. conditions will be passed to `condition-dialog.component`.
             */
            const conds = this.categoryConditions.filter(c => c.category === this.category)?.at(0)?.conditions;
            this.conditions = conds ?? [];
          },
          (error: HttpErrorResponse) => {
            console.error('ProductAddComponent HttpErrorResponse', error);
            this.loading = false;
            if (error.error && error.error.reason) {
              this.errMsg = error.error.reason;
            } else {
              this.errMsg = error.message;
            }
          },
        );
      },
      (error: HttpErrorResponse) => {
        console.error('ProductAddComponent HttpErrorResponse', error);
        this.loading = false;
        if (error.error && error.error.reason) {
          this.errMsg = error.error.reason;
        } else {
          this.errMsg = error.message;
        }
      },
    );
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('FindMatchComponent ngOnDestroy() called.');

    this.destroyed$.next();
    this.destroyed$.complete();
  }

  changeCategory() {
    const cat = this.formGroup.get('category')?.value;
    this.category = cat ? cat : '';
    const cond = this.categoryConditions.filter(e => e.category === this.category).at(0)?.conditions;
    this.conditions = cond ? cond : [];

    console.debug('---- cat:', cat, 'cond: ', cond);
  }

  gohome() {
    this.router.navigate(['/home']);
  }

  conditions: string[] = [];

  async onSubmit() {
    this.openDialog();
  }

  onKeyUp() { }

  condition = '';

  openDialog(): void {

    if (this.conditions.length === 0) {
      console.debug('---------------- conditions.length is 0');
    }

    /**
     * !!! Trick !!!
     *
     * 1. Create and assign dialog data step by step, to prevent dialog window loss sync with data.
     * 2. If create data inline inside open(ConditionDialog, {data: {...}}), dialog window will open without data.
     */
    const conditionDialogData: ConditionDialogData = {
      condition: this.condition,
      conditions: this.conditions,
    } as ConditionDialogData;

    const matDialogConfig: MatDialogConfig = {
      data: conditionDialogData
    };

    console.debug('---- opening dialog with data:', matDialogConfig);

    const conditionDialogRef = this.matDialog.open<ConditionDialogComponent, ConditionDialogData>(ConditionDialogComponent, matDialogConfig);

    conditionDialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed.', result);
      if (result && result != 'noClick') {
        this.condition = result;
        this.toDraft();
      }
    });
  }

  toDraft() {
    const navigationExtras: NavigationExtras = {
      state: {
        title: this.title,
        category: this.category,
        condition: this.condition
      } as any
    };

    this.router.navigate(['/sell/draft'], navigationExtras);
  }
}

