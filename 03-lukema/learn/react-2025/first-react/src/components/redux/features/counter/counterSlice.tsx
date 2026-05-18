import { createSlice } from '@reduxjs/toolkit'


export interface CounterState {
  // [key: string]: number;
  age: number,
  weight: number
}

export interface CounterAction {
  type: string
  payload: CounterState
}

export const counterSlice = createSlice({
  name: 'counter',
  initialState: {
    age: 2,
    weight: 10,
  } as CounterState,
  reducers: {
    increment: (state: CounterState) => {
      // Redux Toolkit allows us to write "mutating" logic in reducers. It
      // doesn't actually mutate the state because it uses the Immer library,
      // which detects changes to a "draft state" and produces a brand new
      // immutable state based off those changes.
      // Also, no return statement is required from these functions.
      state.age += 1
    },
    decrement: (state: CounterState) => {
      state.age -= 1
    },
    incrementByAmount: (state: CounterState, action: CounterAction) => {
      state.age += action.payload.age
    },
  },
})

// Action creators are generated for each case reducer function
export const { increment, decrement, incrementByAmount } = counterSlice.actions

export default counterSlice.reducer
