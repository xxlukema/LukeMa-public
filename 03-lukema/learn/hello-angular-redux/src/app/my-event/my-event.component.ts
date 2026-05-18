import { EventService, MyEvent } from '@/app/utils/events/event.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Data } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  standalone: false,
  selector: 'app-my-event',
  templateUrl: './my-event.component.html',
  styleUrls: ['./my-event.component.scss']
})
export class MyEventComponent implements OnInit, OnDestroy {

  event$: Subscription;

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private readonly eventService: EventService
  ) {
    this.event$ = this.eventService.eventListener().subscribe({
      next: (event: MyEvent) => {
        /**
         * KEEP! Do NOT remove.
         *
         * If 'event.counter' and 'event.name' are from different source, with one source emit 'counter'
         * and another source emit 'name', use the following 'if...else if...' to detect the source of event:
         */
        /*
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
  }


  from = '';

  /**
   * Event properties
   */
  eventCounter!: number;
  eventName!: string;

  ngOnInit(): void {

    console.debug('this.activatedRoute', this.activatedRoute);
    const data: Data = this.activatedRoute.snapshot.data;
    this.from = data.from;

    if (!this.eventCounter) {
      console.debug('MyEventComponent - By default, eventCounter: number is undefined: ', this.eventCounter);
      this.eventCounter = 10;

      this.eventService.emitEvent({
        name: 'init',
        counter: this.eventCounter
      });
    } else {
      console.debug('MyEventComponent eventCounter is: ', this.eventCounter);
    }
  }

  add() {
    this.eventCounter++;

    this.eventService.emitEvent({
      name: 'add',
      counter: this.eventCounter
    });
  }

  deduct() {
    this.eventCounter--;

    this.eventService.emitEvent({
      name: 'subtract',
      counter: this.eventCounter
    });
  }

  toggle = false;

  changeNameOnly() {
    this.toggle = !this.toggle;

    /**
     * N.B. Only 'name' is sent. 'counter' is not sent. Listener will not receive 'name'. Listener will not receive counter data.
     * See `console.debug('NavComponent event: ', event);` from `src\app\nav\nav.component.ts`
     */
    if (this.toggle) {
      this.eventService.emitEvent({
        name: 'add'
      });
    } else {
      this.eventService.emitEvent({
        name: 'minus'
      });
    }
  }

  ngOnDestroy(): void {
    if (this.event$) {
      this.event$.unsubscribe();
    }
  }
}
