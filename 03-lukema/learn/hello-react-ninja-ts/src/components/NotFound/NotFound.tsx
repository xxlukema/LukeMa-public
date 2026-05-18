import { Link } from "react-router-dom";
import styles from './NotFound.module.scss';

const NotFound = () => (
  <div className={styles.NotFound}>
    <h2>Sorry</h2>
    <p>That page cannot be found</p>
    <Link to="/">Back to the homepage...</Link>
  </div>
);

export default NotFound;
