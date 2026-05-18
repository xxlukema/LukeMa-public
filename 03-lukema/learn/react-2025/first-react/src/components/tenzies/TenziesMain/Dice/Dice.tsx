import './Dice.scss'
import { DiceData } from './DiceData'


export const Dice = (props: DiceData) => {

  /**
   * KEEP! This is how to pass the event from child to parent, and to pass a mouse event type
   *
  const handleDiceClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    console.debug('Dice clicked from child', event.currentTarget.id)

    props.handleDiceClick(event)
  }
  */

  const styles = { backgroundColor: props.isSelected ? '#59E391' : 'white' }

  return (
    <button className='dice'
      id={props.id}
      style={styles}
      onClick={() => props.handleDiceSelection(props.id)}>
      {props.value}
    </button>
  )
}
