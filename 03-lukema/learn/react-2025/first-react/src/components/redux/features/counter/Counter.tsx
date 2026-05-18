import { useDispatch, useSelector } from 'react-redux';
import './Counter.scss';
import { CounterState, decrement, incrementByAmount } from './counterSlice';


export const Counter = () => {
  const counterAge = useSelector((state: { counter: CounterState }) => state.counter.age)
  const dispatch = useDispatch()

  return (
    <fieldset className="card">
      <legend>Redux in Counter</legend>

      <div>
        <button onClick={() => dispatch(decrement())}>[-]</button>
        <span>Counter.age: {counterAge}</span>
        <button onClick={() => dispatch(incrementByAmount({age: 2, weight: 0}))}>[+]</button>
      </div>
    </fieldset>
  )
}
