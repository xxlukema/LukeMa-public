import './page.css'


import React from 'react'

const LearnTailwind: React.FC = () => {
  return (
    <div>
      <fieldset className='bg-violet-300 flex flex-col items-center justify-center
                       border-1 border-gold-500 border-dashed rounded-md shadow-2xl shadow-amber-400'>
        <legend className='text-amber-500 text-[1.2rem] font-mono font-bold
                          border-2 border-amber-400 rounded-full px-4 py-2'>Hello Tailwind</legend>
        <div >----------------</div>
        <div className='text-blue-400 mt-[1rem] font-mono font-bold text-[1.5rem]
                        border-dashed border-2 border-amber-400 rounded-md bg-emerald-200 p-10'
          style={{ marginBottom: '0.1rem' }}>
          Hello Tailwind
        </div>
      </fieldset>

      <fieldset className='flex flex-col items-center justify-evenly gap-6 mt-[2rem] pt-[2rem] border-amber-400 border-2 rounded-md' style={{ marginTop: '0.1rem' }}>
        <legend className='text-amber-500 text-[1.2rem] font-mono font-bold
                          border-2 border-amber-400 rounded-full px-4 py-2'>Vertical circles</legend>
        <div className='h-16 w-16 rounded-full bg-blue-500'></div>
        <div className='h-16 w-16 rounded-full bg-orange-500'></div>
        <div className='h-16 w-16 rounded-full bg-green-500'></div>
      </fieldset>

      <fieldset className='grid grid-cols-3 gap-18 mt-2 mx-2'>
        <legend className='text-amber-500 text-[1.2rem] font-mono font-bold
                          border-2 border-amber-400 rounded-full px-4 py-2'>Horizontal circles</legend>
        <div className='h-16 rounded-full bg-blue-500'></div>
        <div className='h-16 rounded-full bg-orange-500'></div>
        <div className='h-16 rounded-full bg-green-500'></div>
      </fieldset>

      <fieldset className='sm:bg-amber-500 md:bg-amber-700 lg:bg-amber-800 xl:bg-amber-900'>
        <legend className='text-amber-500 text-[1.2rem] font-mono font-bold
                          border-2 border-amber-400 rounded-full px-4 py-2'>Mobile first</legend>
        <p className='text-white'>
          I appear on screen wider than 640px
        </p>
      </fieldset>

      <fieldset className='color-chestnut bg-white dark:bg-black text-black
                    dark:text-white text-[1.8rem]'>
        <legend className='text-amber-500 text-[1.2rem] font-mono font-bold
                          border-2 border-amber-400 rounded-full px-4 py-2'>Dark mode</legend>
        This is in dark mode.
      </fieldset>
    </div>
  )
}

export default LearnTailwind
