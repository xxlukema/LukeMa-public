import './TenziesHeader.scss'
import TenziesLogo from '@/assets/Tenzies/color-sample.png'

export const TenziesHeader = () => {
  return (
    <header className="tenzies-header">
      <link href='https://fonts.googleapis.com/css?family=Inter' rel='stylesheet'></link>
      <img src={TenziesLogo} alt="tenzies logo" />
      <h2>Tenzies</h2>
    </header>
  )
}
