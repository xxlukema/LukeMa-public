import { Link, Outlet } from "react-router-dom";
import './Layout.scss';


export const Layout = () => {
  return (
    <>
      <nav className="top-van-bar">
        <ul>
          <li>
            <Link to="/">Tenzies</Link>
          </li>
          <li>
            <Link to="/openai-learning">OpenAIPlatform</Link>
          </li>
          <li>
            <Link to="/ai-learning">AI Learning</Link>
          </li>
          <li>
            <Link to="/endgame">End Game</Link>
          </li>
          <li>
            <Link to="/chef">Chef</Link>
          </li>
          <li>
            <Link to="/state">State</Link>
          </li>
          <li>
            <Link to="/meme">Meme</Link>
          </li>
          <li>
            <Link to="/windowTracker">WindowTracker</Link>
          </li>
          <li>
            <Link to="/app">App</Link>
          </li>
        </ul>
      </nav>

      <Outlet />
    </>
  )
};
