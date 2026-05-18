import { Component, OnDestroy, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss'],
})
export class ContactComponent implements OnInit, OnDestroy {
  otherKeys = [
    '-',
    '.',
    ',',
    'Backspace',
    'ArrowLeft',
    'ArrowRight',
    'Delete',
    'Insert',
  ];
  data = '';

  email = 'xianliu_ma@freddiemac.com';

  sub$: Subscription;

  refreshing = false;

  constructor(private router: Router) {
    this.sub$ = this.router.events.subscribe((e: any) => {
      if (e instanceof NavigationEnd) {
        this.refresh();
      }
    });
  }

  /**
   * (keydown)  --- Triggers (1st) every time a key is pushed down
   * (keypress) --- Triggers (2nd. Donnot use because its keyCode is different. And it can be filtered out by keydown event)
   *                when key presses but doesn't trigger on certain keystrokes like the backspace.
   * (keyup)    --- Triggers (3rd) every time a key push event has completed
   * (change)   --- Triggers (4th) when the input loses focus and value change
   * (focusout) --- Triggers (last) when the input loses focus
   *
   * <input (keydown.enter)='...'>
   * <input (keydown.a)='...'>
   * <input (keydown.esc)='...'>
   * <input (keydown.shift.esc)='...'>
   * <input (keydown.control)='...'>
   * <input (keydown.alt)='...'>
   * <input (keydown.meta)='...'>
   * <input (keydown.9)='...'>
   * <input (keydown.tab)='...'>
   * <input (keydown.backspace)='...'>
   * <input (keydown.arrowup)='...'>
   * <input (keydown.shift.arrowdown)='...'>
   * <input (keydown.shift.control.z)='...'>
   * <input (keyup.control.1)='onKeydown($event)'>
   * <input (keydown.f4)='...'>
   *
   */

  keydown($event) {
    this.data = $event.target.value;
    console.log(
      'keydown: data=',
      this.data,
      ' $event.key=',
      $event.key,
      ' $event.keyCode=',
      $event.keyCode,
      ' $event.type=',
      $event.type
    );
    const code = $event.keyCode;
    if (!((code > 47 && code < 58) || this.otherKeys.includes($event.key))) {
      /**
       * 1. Reject cut/copy/paste to prevent non-digits get pasted
       */
      $event.preventDefault();
    }
  }
  keyup($event) {
    // this.data = $event.target.value;
    // console.log('keyup: data', this.data, '$event.key', $event.key, '$event.keyCode', $event.keyCode, '$event.type', $event.type);
  }
  keypress($event) {
    // this.data = $event.target.value;
    // console.log('keypress: data', this.data, '$event.key', $event.key, '$event.keyCode', $event.keyCode, '$event.type', $event.type);
  }
  change($event) {
    // this.data = $event.target.value;
    // console.log('change: data', this.data, '$event.key', $event.key, '$event.keyCode', $event.keyCode, '$event.type', $event.type);
  }
  focusout($event) {
    // this.data = $event.target.value;
    // console.log('focusout: data', this.data, '$event.key', $event.key, '$event.keyCode', $event.keyCode, '$event.type', $event.type);
  }
  isCutCopyPaste($event) {
    return (
      $event.ctrlKey &&
      ($event.key === 'c' || $event.key === 'x' || $event.key === 'p')
    );
  }

  refresh() {
    console.log('ContactComponent refresh()');

    this.refreshing = true;

    // Do rest call here

    setTimeout(() => {
      this.refreshing = false;
    }, 300);
  }

  /**
   * Called whenever entering the page/template.
   */
  ngOnInit() {
    console.log('ContactComponent ngOnInit() called.');
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy() {
    console.log('ContactComponent ngOnDestroy() called.');
    this.sub$.unsubscribe();
  }
}
