import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';


export interface MyEvent {
  counter?: number,
  name?: string
}

@Injectable({
  providedIn: 'root',
})
export class EventService {

  /**
   * 'Event' is actually rxjs.BehaviorSubject. That is why it works the same as `src\app\utils\rxjs\my-observable.service.ts`.
   *
   * https://stackoverflow.com/questions/43348463/what-is-the-difference-between-subject-and-behaviorsubject
   *
   * BehaviorSubject vs Subject vs ReplaySubject vs AsyncSubject
   */
  private event: Subject<MyEvent> = new BehaviorSubject<MyEvent>({});

  emitEvent(event: MyEvent): void {
    this.event.next(event);
  }

  eventListener(): Observable<MyEvent> {
    return this.event.asObservable();
  }
}
