import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { DateUpdated } from './date-updated.rest';
import { House } from './house.rest';
import { PropertyReportService } from './property-report.service';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { PropertyReportRoutingModule } from './property-report-routing.module';
import { FooterComponent } from '../footer/footer.component';
import { MinusSignToParensPipe } from '../pipes/minus-sign-to-parens.pipe';

@Component({
  selector: 'app-property-report',
  templateUrl: './property-report.component.html',
  styleUrls: ['./property-report.component.scss'],
  imports: [
    CommonModule,
    FlexLayoutModule,
    FooterComponent,
    PropertyReportRoutingModule,
    MinusSignToParensPipe
  ]
})
export class PropertyReportComponent implements OnInit, OnDestroy {
  constructor(
    private readonly propertyReportService: PropertyReportService,
    private readonly router: Router,
    private readonly route: ActivatedRoute) {

    console.log('property-report', 'constructor');

    /** 1/2 Get queryParamMap first before call refresh(). */
    this.sub$ = this.route.queryParamMap.subscribe(params => {
      this.user = params.get('user') ?? 'default';
    });

    console.log('property-report', 'constructor', 'user', this.user);

    /** 2/2 Get queryParamMap first before call refresh(). */
    this.channel$ = this.router.events.subscribe((e: any) => {
      if (e instanceof NavigationEnd) {
        this.refresh();
      }
    });
  }

  channel$: Subscription;
  sub$: Subscription;

  houses: House[] = [];
  dateUpdated: string = '';
  user = 'luke2';
  refreshing = false;

  ngOnInit(): void {
    console.log('property-report', 'ngOnInit() called.', 'user', this.user);
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('property-report', 'ngOnDestroy() called.');

    /**
     * Unsbuscribe from Observable channels here.
     */
    if (this.channel$) {
      this.channel$.unsubscribe();
    }

    if (this.sub$) {
      this.sub$.unsubscribe();
    }
  }

  getDateUpdated(): any {
    this.refreshing = true;
    this.propertyReportService.getDateUpdated()
      .subscribe({
        next: (data: DateUpdated) => {
          this.refreshing = false;
          this.dateUpdated = data.dateUpdated;
          console.log('property-report', 'getDateUpdated', 'data', data);
        },
        error: (error: any) => {
          console.log('property-report', 'getDateUpdated', 'error', error);
          this.refreshing = false;
          return false;
        }
      }).add(() => {
        console.log('property-report', 'getDateUpdated', 'request successfully complete');
        this.refreshing = false;
      });

    return true;
  }

  getPropertyList(): any {
    this.refreshing = true;
    this.propertyReportService.getPropertyList()
      .subscribe({
        next: (data: House[]) => {
          this.refreshing = false;
          this.houses = data;
          console.log('property-report', 'getPropertyList', 'data', data);
        },
        error: (error: any) => {
          console.log('property-report', 'getPropertyList', 'error', error);
          this.refreshing = false;
          return false;
        }
      })
      .add(() => {
        console.log('property-report', 'getPropertyList', 'request successfully complete');
        this.refreshing = false;
      });

    return true;
  }

  refresh() {
    this.sub$ = this.route.queryParamMap.subscribe(params => {
      this.user = params.get('user') ?? 'default';
    });

    console.log('property-report', 'refresh', 'user', this.user);

    this.refreshing = true;
    console.log('property-report', 'refresh Call getPropertyList()');
    this.houses = [];

    setTimeout(() => {
      this.getDateUpdated();
      this.getPropertyList();
    }, 100);
  }
}
