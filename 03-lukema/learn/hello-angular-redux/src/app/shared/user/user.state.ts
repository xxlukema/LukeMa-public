import { User } from './user.type';

export interface UserState {
  readonly user: User;
}

export const initialState: UserState = {
  user: new User()
};
