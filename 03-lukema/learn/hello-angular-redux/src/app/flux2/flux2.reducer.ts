import { Action, createReducer, on } from '@ngrx/store';
import * as Flux2ActionTypes from './flux2.action';
import { Flux2State, initialState2 } from './flux2.state';

export const flux2FeatureKey = 'flux2State';

const newFlux2Reducer = createReducer(
  initialState2,
  on(Flux2ActionTypes.Increment,
    (state: Flux2State, props) => {
      return {
        ...props.payload,
        flux2Data: {
          counter: state.flux2Data.counter + props.payload.flux2Data.counter,
        },
      };
    }),
  on(Flux2ActionTypes.Decrement,
    (state: Flux2State, props) => {
      return {
        ...props.payload,
        flux2Data: {
          counter: state.flux2Data.counter - props.payload.flux2Data.counter,
        },
      };
    })
);

export function flux2Reducer(state: Flux2State = initialState2, action: Action) {
  return newFlux2Reducer(state, action);
}
