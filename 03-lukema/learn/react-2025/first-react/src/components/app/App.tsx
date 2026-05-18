import reactLogo from '@/assets/react.svg'
import viteLogo from '@/assets/vite-logo.svg'
import { Counter } from '@/components/redux/features/counter/Counter'
import { CounterState } from '@/components/redux/features/counter/counterSlice'
import { useZustandCounterState } from '@/components/zustand/store.ts'
import { useState } from 'react'
import { useSelector } from 'react-redux'
import { ZustandCounter } from '../zustand/features/ZustandCounter'
import './App.scss'


export const App = () => {
  const [count, setCount] = useState(0)
  const counter: CounterState = useSelector((state: { counter: CounterState }) => state.counter);
  const { zustandCounter, incrementByAmount } = useZustandCounterState()

  return (
    <>
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>

      <h1>Vite + React Local Counter</h1>

      <button onClick={() => setCount((count) => count + 1)}>
        Click to increment count: {count}
      </button>

      <h1>Redux Counter</h1>

      {
        console.log("Counter: ", counter)
      }

      <fieldset className="card">
        <legend>Redux in App</legend>

        <div>Counter.age: {counter['age']}</div>
      </fieldset>

      <Counter />

      <h1>Zustand Counter</h1>

      <fieldset className="card">
        <legend>Zustand in App</legend>

        <div>Zustand Counter: {zustandCounter}</div>

        <button onClick={() => incrementByAmount(2)}>
          Increment by (2)
        </button>

      </fieldset>

      <ZustandCounter />

      <hr />

      <h2>Tailwindcss</h2>

      <div>
        <div className='bg-violet-200 flex flex-col items-center justify-center h-10 w-full border-2 border-gold-500 border-solid
                       rounded-md shadow-lime-100 my-0 p-200'>
          <div >----------------</div>
          <div className='text-blue-400 mt-[3rem] font-mono font-extrabold text-[1.5rem]
                       border-dashed border-2 border-amber-400 round-1' style={{ marginBottom: '1.5rem' }}>Hello Tailwind</div>


        </div>

        <div className='flex flex-col items-center justify-evenly gap-6 mt-[2rem] pt-[2rem] border-amber-400 border-2 rounded-md' style={{ marginTop: '0.1rem' }}>
          <div className='h-16 w-16 rounded-full bg-blue-500'></div>
          <div className='h-16 w-16 rounded-full bg-orange-500'></div>
          <div className='h-16 w-16 rounded-full bg-green-500'></div>
        </div>

        <div className='grid grid-cols-3 gap-4 mt-2 mx-2'>
          <div className='h-16 rounded-full bg-blue-500'></div>
          <div className='h-16 rounded-full bg-orange-500'></div>
          <div className='h-16 rounded-full bg-green-500'></div>
        </div>

        <div className='sm:bg-amber-500 md:bg-amber-700 lg:bg-amber-800 xl:bg-amber-900'>
          <p className='text-white'>
            I appear on screen wider than 640px
          </p>
        </div>

        <div className='color-chestnut bg-white dark:bg-black text-black
                    dark:text-white text-[1.8rem]'>
          This is in dark mode.
        </div>
      </div>

    </>
  )
}

