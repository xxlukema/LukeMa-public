import LM from '@/assets/LM-1.png'
import './Header.scss'

export const Header = () => {
  return (
    <header className='header'>
      <link href='https://fonts.googleapis.com/css?family=Inter' rel='stylesheet'></link>

      <img src={LM} alt="LM-1.png" height={80} />
      <nav className='header-nav'>
        <ul>
          <li>
            <a href='#'>Price</a>
          </li>
          <li>
            <a href='#'>About</a>
          </li>
          <li>
            <a href='#'>Contact</a>
          </li>
        </ul>
      </nav>

    </header>
  )
}
