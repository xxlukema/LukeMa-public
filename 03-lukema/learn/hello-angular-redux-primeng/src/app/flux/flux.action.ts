
// Section 1
import { createAction, props } from '@ngrx/store';
import { FluxData } from './flux.state';

// Section 2
export enum FluxActionTypes {
  INCREMENT = '[Flux] Increment',
  DECREMENT = '[Flux] Decrement'
}

// Section 3
export const IncrementCounter = createAction(FluxActionTypes.INCREMENT, props<{ payload: { fluxData: FluxData } }>());
export const DecrementCounter = createAction(FluxActionTypes.DECREMENT, props<{ payload: { fluxData: FluxData } }>());
