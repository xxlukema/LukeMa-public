import * as FluxSlectors from '@/app/flux/flux.selector';
import { FluxData, FluxState } from '@/app/flux/flux.state';
import { flux2Selector } from '@/app/flux2/flux2.selector';
import { Flux2Data, Flux2State } from '@/app/flux2/flux2.state';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { select, Store } from '@ngrx/store';
import { catchError, map, Observable, Subscription } from 'rxjs';
import { MyInjectable } from '@/app/injectable/my-injectable';
import { EventService, MyEvent } from '@/app/utils/events/event.service';
import { MyObservableService } from '@/app/utils/rxjs/my-observable.service';
import { NmsService } from '@/app/utils/nms.service';

@Component({
  standalone: false,
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css'],
})
export class NavComponent implements OnInit, OnDestroy {
  constructor(
    private readonly store: Store<FluxState>,
    private readonly store2: Store<Flux2State>,
    public readonly myInjectable: MyInjectable,
    private readonly router: Router,
    private readonly eventService: EventService,
    private readonly myObservableService: MyObservableService,
    private readonly nmsService: NmsService
  ) {
    this.fluxData = this.store.select(FluxSlectors.fluxSelector);
    this.flux2Data = this.store2.pipe(select(flux2Selector));

    this.event$ = this.eventService.eventListener().subscribe({
      next: (event: MyEvent) => {
        console.debug('NavComponent event: ', event);

        /**
         * KEEP! Do NOT remove.
         *
         * If 'event.counter' and 'event.name' are from different source, with one source emit 'counter'
         * and another source emit 'name', use the following 'if...else if...' to detect the source of event:
         */
        /**
        if (event.counter) {
          this.eventCounter = event.counter;
        } else if (event.name) {
          this.eventName = event.name;
        }
        */

        /**
         * In case both 'counter' and 'name' are from the same source, and there is no need to detect source:
         */
        if (event.counter) {
          this.eventCounter = event.counter;
        }
        if (event.name) {
          this.eventName = event.name;
        }
      }
    });

    this.myObservable$ = this.myObservableService.getDataObject().pipe(
      map((response: number) => {
        /**
         * 'response' can be intercepted inside mapper. For example, add 23 to response. But it can only influnce downstream data for "this"
         * subscriber. It cannot affect data in other pipeline.
         */
        response += 23;

        console.log('value from service: ' + response);
        return response;
      }),
      catchError(this.nmsService.handleError)
    );
  }

  event$: Subscription;

  myChannel$!: Subscription;

  myObservable$: Observable<number>;
  myObservedNumber = 0;

  fluxData: Observable<FluxData>;
  flux2Data: Observable<Flux2Data>;

  /**
   * Event properties
   */
  eventCounter!: number;
  eventName!: string;

  ngOnInit(): void {
    if (!this.eventCounter) {
      console.debug('NavComponent - By default, eventCounter: number is undefined: ', this.eventCounter);
      /** this.eventCounter = 5; */
    } else {
      console.debug('NavComponent eventCounter is: ', this.eventCounter);
    }

    this.myChannel$ = this.myObservable$.subscribe({
      next: (data: number) => {
        this.myObservedNumber = data;
      }
    });
  }

  ngOnDestroy(): void {
    if (this.event$) {
      this.event$.unsubscribe();
    }
    if (this.myChannel$) {
      this.myChannel$.unsubscribe();
    }
  }

}
