import { Action, createReducer, on } from '@ngrx/store';
import * as FluxActionTypes from './flux.action';
import { FluxState, initialState } from './flux.state';

export const fluxFeatureKey = 'fluxState';

const newFluxReducer = createReducer(
  initialState,
  on(FluxActionTypes.IncrementCounter,
    (state: FluxState, props) => {
      return {
        ...props.payload,
        fluxData: {
          counter: state.fluxData.counter + props.payload.fluxData.counter
        }
      };
    }),
  on(FluxActionTypes.DecrementCounter,
    (state: FluxState, props) => {
      return {
        ...props.payload,
        fluxData: {
          counter: state.fluxData.counter - props.payload.fluxData.counter
        }
      };
    })
);

export function fluxReducer(state: FluxState = initialState, action: Action): FluxState {
  return newFluxReducer(state, action);
}

