import styles from './NavBar.module.scss';
import { Link } from "react-router-dom";

const NavBar = () => {
  return (
    <nav className={styles.NavBar}>
      <h1>The Dojo Blog</h1>

      <div className="links">
        <Link to="/">Home</Link>
        <Link to="/create" style={{
          color: 'white',
          backgroundColor: '#f1356d',
          borderRadius: '8px'
        }}>New Blog</Link>
      </div>
    </nav>
  );
}

export default NavBar;
