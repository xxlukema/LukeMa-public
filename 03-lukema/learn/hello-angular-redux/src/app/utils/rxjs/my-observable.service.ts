import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MyObservableService {

  /**
   * 'Event' is actually rxjs.BehaviorSubject. That is why it works the same as `src\app\utils\rxjs\my-observable.service.ts`.
   *
   * https://stackoverflow.com/questions/43348463/what-is-the-difference-between-subject-and-behaviorsubject
   *
   * BehaviorSubject vs Subject vs ReplaySubject vs AsyncSubject
   */
  subject: Subject<number> = new Subject();

  publishDataObject(obj: number): void {
    this.subject.next(obj);
  }

  getDataObject(): Observable<number> {
    return this.subject.asObservable();
  }
}
