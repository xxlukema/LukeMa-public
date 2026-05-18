import * as Flux2Actions from '@/app/flux2/flux2.action';
import { flux2Selector } from '@/app/flux2/flux2.selector';
import { Flux2Data, Flux2State } from '@/app/flux2/flux2.state';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  standalone: false,
  selector: 'app-flux2-demo',
  templateUrl: './flux2-demo.component.html',
  styleUrls: ['./flux2-demo.component.scss']
})
export class Flux2DemoComponent implements OnInit, OnDestroy {

  constructor(private readonly store2: Store<Flux2State>,
    private readonly route: ActivatedRoute,
    private readonly router: Router) {
    this.flux2Data = this.store2.pipe(select(flux2Selector));
  }

  flux2Data: Observable<Flux2Data>;

  decrement(): void {
    console.log('Flux2 decrement clicked.');
    this.store2.dispatch(Flux2Actions.Decrement({
      payload: {
        flux2Data: { counter: 1 }
      }
    }));
  }

  increment(): void {
    console.log('Flux2 increment clicked.');
    this.store2.dispatch(Flux2Actions.Increment({
      payload: {
        flux2Data: { counter: 2 }
      }
    }));
  }

  /**
   * Called whenever entering the page/template.
   */
  ngOnInit(): void {
    console.log('Flux2DemoComponent ngOnInit() called.');
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('Flux2DemoComponent ngOnDestroy() called.');
    /**
     * Unsbuscribe from Observable channels here.
     */
  }


  sendMessage() {
    this.router.navigate(['/flux-demo'], {
      relativeTo: this.route,
      replaceUrl: true,
      queryParams:
      {
        surveyInstanceId: 202,
      }
    });
  }



}
