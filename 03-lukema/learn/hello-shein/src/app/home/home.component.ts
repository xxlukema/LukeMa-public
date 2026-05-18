import { Item } from '@/app/sell/draft/draft.service';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Router } from '@angular/router';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { env } from 'environments/environment';
import { Subject, takeUntil } from 'rxjs';
import { LoadingModule } from '../utils/loading/loading.module';
import { NmsService } from '../utils/services/nms.service';
import { HomeService } from './home.service';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule, FlexLayoutModule, MatInputModule,
    MatButtonModule,
    FormsModule,
    MatTooltipModule,
    LoadingModule,
    ReactiveFormsModule
  ],
  providers: [
    HomeService
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {

  constructor(
    private router: Router,
    public nmsService: NmsService,
    private homeService: HomeService
  ) { }

  private readonly destroyed$ = new Subject<void>();

  recentViewed: string[] = [];

  items: Item[] = [];

  errMsg = '';

  titleMaxLlength = 120;

  loading = false;

  imageUrlPrefix = env.imageUrlPrefix;

  @ViewChild('panel') public panel!: ElementRef<any>;

  formGroup = new FormGroup({
    /** required */
    searchText: new FormControl('', []),
  });

  onKeyUp() {
  }

  onSubmit() { }

  decrease() {
    if (this.panel.nativeElement.scrollLeft === 0) {
      return;
    }
    this.panel.nativeElement.scrollLeft -= 400;
  }


  increase() {
    this.panel.nativeElement.scrollLeft += 400;
  }

  /**
   * Called whenever entering the page/template.
   */
  ngOnInit(): void {
    console.log('HomeComponent ngOnInit() called.');
    this.homeService.getAllItems().pipe(takeUntil(this.destroyed$)).subscribe({
      next: (response) => {
        // this.items = response.map(e => { e.title = e.title.length > this.titleMaxLlength ? e.title.substring(0, this.titleMaxLlength) + '...' : e.title; return e; });
        this.items = response;
        console.debug('---- HomeComponent response', response);
      },
      error: (error: HttpErrorResponse) => {
        console.error('HomeComponent HttpErrorResponse', error);
        if (error.error && error.error.reason) {
          this.errMsg = error.error.reason;
        } else {
          this.errMsg = error.message;
        }
      },
    });

  }

  gotoItem(id: number | null | undefined) {
    if (!id) {
      return;
    }

    this.router.navigate(['/item'], { state: { id } });
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('HomeComponent ngOnDestroy() called.');
    /**
     * Unsbuscribe from Observable channels here.
     */
    this.destroyed$.next();
    this.destroyed$.complete();
  }

}
