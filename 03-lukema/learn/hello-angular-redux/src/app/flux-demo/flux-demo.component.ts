import * as FluxActions from '@/app/flux/flux.action';
import * as FluxSelectors from '@/app/flux/flux.selector';
import { FluxData, FluxState } from '@/app/flux/flux.state';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable, Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  standalone: false,
  selector: 'app-flux-demo',
  templateUrl: './flux-demo.component.html',
  styleUrls: ['./flux-demo.component.scss']
})
export class FluxDemoComponent implements OnInit, OnDestroy {

  constructor(private readonly store: Store<FluxState>,
    private readonly route: ActivatedRoute,
    private readonly router: Router) {
    this.fluxData = store.select(FluxSelectors.fluxSelector);

    this.params$ = this.route.queryParams.subscribe(params => {

      // this.surveyInstanceId = params['surveyInstanceId'];

      // console.log('============== this.surveyInstanceId this.descr:', this.surveyInstanceId);
    });
  }

  fluxData: Observable<FluxData>;
  params$: Subscription;
  surveyInstanceId = 0;

  decrement(): void {
    console.log('Flux decrement clicked.');
    this.store.dispatch(
      FluxActions.DecrementCounter({
        payload: { fluxData: { counter: 1 } }
      })
    );
  }

  increment(): void {
    console.log('Flux increment clicked.');
    this.store.dispatch(
      FluxActions.IncrementCounter({
        payload: { fluxData: { counter: 2 } }
      })
    );
  }

  /**
   * Called whenever entering the page/template.
   */
  ngOnInit(): void {
    console.log('FluxComponent ngOnInit() called.');
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('FluxComponent ngOnDestroy() called.');
    /**
     * Unsbuscribe from Observable channels here.
     */
    if (this.params$) {
      this.params$.unsubscribe();
    }
  }

}
