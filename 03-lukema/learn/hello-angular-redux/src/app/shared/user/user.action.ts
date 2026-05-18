
import { createAction, props } from '@ngrx/store';
import { User } from './user.type';

enum UserActionTypes {
  SET_USER = '[UserState] SET_USER'
}

export const setUser = createAction(UserActionTypes.SET_USER, props<{ payload: { user: User } }>());

