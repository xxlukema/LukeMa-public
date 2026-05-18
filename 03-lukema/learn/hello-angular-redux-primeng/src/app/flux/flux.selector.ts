import { fluxFeatureKey } from '@/app/flux/flux.reducer';
import { FluxState } from '@/app/flux/flux.state';
import { createFeatureSelector, createSelector } from '@ngrx/store';

const mySelectFluxState = createFeatureSelector<FluxState>(
  fluxFeatureKey,
);

export const fluxSelector = createSelector(
  mySelectFluxState,
  (state: FluxState) => state.fluxData
);

