// Section 1
import { createAction, props } from '@ngrx/store';
import { Flux2Data } from './flux2.state';

// Section 2
export enum Flux2ActionTypes {
  INCREMENT = '[Flux2] Increment',
  DECREMENT = '[Flux2] Decrement'
}

// Sections 3
export const Increment = createAction(Flux2ActionTypes.INCREMENT, props<{ payload: { flux2Data: Flux2Data } }>());
export const Decrement = createAction(Flux2ActionTypes.DECREMENT, props<{ payload: { flux2Data: Flux2Data } }>());
