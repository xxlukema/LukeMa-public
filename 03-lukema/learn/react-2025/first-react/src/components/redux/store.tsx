import { configureStore } from '@reduxjs/toolkit';
import counterReducer from '@/components/redux/features/counter/counterSlice'


export const store = configureStore({
  reducer: {
    counter: counterReducer
  },
  devTools: process.env.NODE_ENV !== 'production',
});
