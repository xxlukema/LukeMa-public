# Zustand

    npm install zustand

## Store

    import { create } from 'zustand';
    
    const useStore = create((set) => ({
      count: 0,
      increment: () => set((state) => ({ count: state.count + 1 })),
      decrement: () => set((state) => ({ count: state.count - 1 })),
    }));

## Code

    import React from 'react';
    import useStore from './store';
    
    function Counter() {
      const { count, increment, decrement } = useStore();
    
      return (
        <div>
          <p>Count: {count}</p>
          <button onClick={increment}>Increment</button>
          <button onClick={decrement}>Decrement</button>
        </div>
      );
    }

## Comparison

Summary Table

    Tool           | Best For                        | Boilerplate   | Type
    ---------------|---------------------------------|---------------|-------------
    Redux          | Large, strict state needs       | High          | Centralized
    Context API    | Small apps                      | Low           | Built-in
    Zustand        | All sizes                       | Very Low      | Store-based
    Recoil         | Complex shared state            | Medium        | Atom-based
    Jotai          | Simpler alternative to Recoil   | Very Low      | Atom-based
    React Query    | Server state (API data)         | Low           | Remote state
