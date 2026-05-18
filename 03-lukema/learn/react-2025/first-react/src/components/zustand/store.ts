import { create } from 'zustand';


export interface ZustandCounterState {
  zustandCounter: number;
  increment: () => void;
  decrement: () => void;
  incrementByAmount: (delta: number) => void;
  // [key: string]: any;
}

const useZustandCounterStore = create<ZustandCounterState>((set) => ({
  zustandCounter: -2,
  increment: () => set((state: ZustandCounterState) => ({ zustandCounter: state.zustandCounter + 1 })),
  decrement: () => set((state: ZustandCounterState) => ({ zustandCounter: state.zustandCounter - 1 })),
  incrementByAmount: (delta: number) => set((state: ZustandCounterState) => ({ zustandCounter: state.zustandCounter + delta })),
}));

export default useZustandCounterStore;

/**
 * Usage:
 *     import { useZustandCounterStateValue } from '@/components/zustand/store.ts';
 *     const zustandCounter = useZustandCounterStateValue();
 */
export const useZustandCounterStateValue = () => {
  const { zustandCounter } = useZustandCounterStore();
  return zustandCounter;
};

/**
 * Usage:
 *    import { useZustandCounterState } from '@/components/zustand/store.ts';
 */
export const useZustandCounterState = () => {
  const { zustandCounter, increment, decrement, incrementByAmount } = useZustandCounterStore();
  return { zustandCounter, increment, decrement, incrementByAmount };
};
