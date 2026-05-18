import { combineReducers, createStore } from 'redux';
import FluxCounterReducer from '../../FluxCounter/FluxCounterReducer';
import FluxEventReducer from '../../FluxEvent/FluxEventReducer';

/**
 * If you create a store instance and export it from a module, it will become a singleton.
 */
// export const storeOneReducer = createStore(FluxCounterReducer);
// export const store = createStore(FluxCounterReducer);

const combinedReducer = combineReducers({ fluxCounterReducer: FluxCounterReducer, fluxEventReducer: FluxEventReducer });
export const store = createStore(combinedReducer);
