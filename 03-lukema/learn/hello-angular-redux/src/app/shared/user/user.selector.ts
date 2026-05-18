import { createFeatureSelector, createSelector } from '@ngrx/store';
import { userFeatureKey } from './user.reducer';
import { UserState } from './user.state';

const userStateSelector = createFeatureSelector<UserState>(userFeatureKey);

export const userSelector = createSelector(
  userStateSelector,
  (state: UserState) => state.user
);
