import { Action, createReducer, on } from '@ngrx/store';
import * as UserActions from './user.action';
import { initialState } from './user.state';

export const userFeatureKey = 'userState';

const newUserReducer = createReducer(
  initialState,
  on(UserActions.setUser,
    (state, props) => {
      return {
        ...props.payload
      };
    })
);

export function userReducer(state, action: Action) {
  return newUserReducer(state, action);
}
