import Create from '@/AddBlog/AddBlog';
import BlogDetails from '@/BlogDetails/BlogDetails';
import Home from '@/Home/Home';
import Navbar from '@/NavBar/NavBar';
import NotFound from '@/NotFound/NotFound';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import styles from './App.module.scss';


const App = ({ children }: any) => {

  return (
    <Router>
      <div className={styles.App}>
        <Navbar />
        <div className="content">
          <Switch>
            <Route exact path="/">
              <Home />
            </Route>
            <Route path="/create">
              <Create />
            </Route>
            <Route path="/blogs/:id">
              <BlogDetails />
            </Route>
            <Route path="*">
              <NotFound />
            </Route>
          </Switch>
        </div>
      </div>
    </Router>
  );
}

export default App;
