import React, { useState } from 'react';
import './TenziesMain.scss';
import { Dice } from './Dice/Dice';
import { DiceData } from './Dice/DiceData';
import { nanoid } from 'nanoid';
import Confetti from 'react-confetti';


export const TenziesMain = () => {

  /**
   * Lazy initialization of the dice array
   */
  const [diceArray, setDiceArray] = useState<DiceData[]>(() => [])

  const [isGameWon, setIsGameWon] = useState<boolean>(false)

  /**
   * KEEP! This is to pass a mouse event type
   *
   * const handleDiceClick = (event: React.MouseEvent<HTMLButtonElement>) => {
   *   console.debug('Dice clicked from parent', event.currentTarget.id)
   */
  const handleDiceClick = (id: string) => {
    console.debug('Dice clicked from parent', id)

    const newDiceArray = diceArray.map(el => {
      if (el.id === id) {
        el.isSelected = !el.isSelected
      }
      return el
    })
    setDiceArray(newDiceArray)

    if (diceArray.every(el => el.isSelected)) {
      console.debug('Game Won!')
    }
  }

  React.useEffect(() => {

    const newDiceArray: DiceData[] = new Array<DiceData>(12)
      .fill({
        id: '',
        value: 0,
        isSelected: false,
        handleDiceSelection: () => { }
      }).map(el => ({
        ...el,
        id: nanoid(),
        value: Math.ceil(Math.random() * 6)
      }))

    setDiceArray(newDiceArray)

    console.debug('TenziesMain mounted --------')

    return () => {
      console.debug('TenziesMain unmounted --------')
    }
  }, [])

  React.useEffect(() => {

    console.debug('Dice Array or isGameWon changed')

    setIsGameWon(diceArray.every(el => el.isSelected))

    if (isGameWon) {
      console.debug('Game Won!')

      // document.getElementById('btn-roll')?.focus()
      btnRef.current?.focus()
    }
  }, [diceArray, isGameWon])

  const btnRef = React.useRef<HTMLButtonElement>(null)

  const rollDice = (): void => {
    console.debug('Roll dice clicked')

    /**
     * If all are selected, then deselect the clicked dice
     */
    const count = diceArray.filter(el => el.isSelected).length
    if (count === diceArray.length) {
      const newDiceArray = diceArray.map(el => {
        el.isSelected = false
        return el
      })

      setDiceArray(newDiceArray)
    }

    const newDiceArray = diceArray.map(el => {
      if (!el.isSelected) {
        el.value = Math.floor(Math.random() * 6) + 1
      }
      return el
    })

    setDiceArray(newDiceArray)
  }

  return (
    <section className='main-section'>
      {
        isGameWon && <Confetti
          recycle={false}
          numberOfPieces={200}
        />
      }
      <div className='tenzies-main-out'>
        <div className='tenzies-main-inner'>
          {
            diceArray.map(el => <Dice key={el.id} {...el} handleDiceSelection={handleDiceClick}></Dice>)
          }
          <button ref={btnRef} id='btn-roll' className='btn-roll' onClick={rollDice}>
            {isGameWon ? 'New Game' : 'Roll Dices'}
          </button>
        </div>
      </div>
    </section>
  )
}
