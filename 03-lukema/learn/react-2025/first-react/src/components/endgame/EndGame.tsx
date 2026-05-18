import Footer from '@/components/footer/Footer'
import './EndGame.scss'
import { EndGameHeader } from './EndGameHeader/EndGameHeader'
import { EndGameMain } from './EndGameMain/EndGameMain'


export const EndGame = () => {
  return (
    <div className="endgame-main">
      <EndGameHeader />
      <EndGameMain />
      <Footer />
    </div>
  )
}
