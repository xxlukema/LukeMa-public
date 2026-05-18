import React from 'react';
import ReactDOM from 'react-dom';
import './index.scss';
import App from './App.jsx';
import About from './About.jsx';
import Home from './Home.jsx';
import Contact from '../Contact.jsx';
import * as serviceWorker from '../serviceWorker';
// import { Router, Route, Link, browserHistory } from 'react-router'
import { BrowserRouter } from 'react-router-dom';

// ReactDOM.render(<App headerProp="Header from props..." contentProp="Content
//    from props..."/>, document.getElementById('root'));

/*
ReactDOM.render((
   <Router history={browserHistory}>
      <Route path="/" component={App}>
         <Route path="home" component={Home} />
         <Route path="about" component={About} />
         <Route path="contact" component={Contact} />
      </Route>
   </Router>
), document.getElementById('root'));
*/

ReactDOM.render((
   <BrowserRouter history={browserHistory}>
      <Route path="/" component={App}>
         <Route path="home" component={Home} />
         <Route path="about" component={About} />
         <Route path="contact" component={Contact} />
      </Route>
   </BrowserRouter>
), document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
