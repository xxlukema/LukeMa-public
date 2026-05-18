import useZustandCounterStore from '@/components/zustand/store.ts';
import './ZustandCounter.scss';


export const ZustandCounter = () => {
  const { zustandCounter, increment, decrement } = useZustandCounterStore();

  return (
    <fieldset className="card">
      <legend>Zustand in Counter</legend>

      <div>
        <button onClick={() => decrement()}>[-]</button>
        <span>Zustand Counter: {zustandCounter}</span>
        <button onClick={() => increment()}>[+]</button>
      </div>
    </fieldset>
  );
}

