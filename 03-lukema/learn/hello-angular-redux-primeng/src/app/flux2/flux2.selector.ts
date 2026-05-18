import { flux2FeatureKey } from '@/app/flux2/flux2.reducer';
import { Flux2State } from '@/app/flux2/flux2.state';
import { createFeatureSelector, createSelector } from '@ngrx/store';

const mySelectFlux2State = createFeatureSelector<Flux2State>(
  flux2FeatureKey,
);

export const flux2Selector = createSelector(
  mySelectFlux2State,
  (state: Flux2State) => state.flux2Data
);
