
# Why we need to bind event handlers in Class Components in React

https://www.freecodecamp.org/news/this-is-why-we-need-to-bind-event-handlers-in-class-components-in-react-f7ea1a6f93eb/

Arrow functions are exempt from this behavior because they use lexical this binding which automatically binds them to
the scope they are defined in.

Why don’t we need to bind ‘this’ for Arrow functions?

We have two more ways we can define event handlers inside a React component.

    Public Class Fields Syntax(Experimental)

      class Foo extends React.Component{
        handleClick = () => {
          console.log(this); 
        }
      
        render(){
          return (
            <button type="button" onClick={this.handleClick}>
              Click Me
            </button>
          );
        }
      } 

    Arrow function in the callback

      <button type="button" onClick={(e) => this.handleClick(e)}>
        Click Me
      </button>

Both of these use the arrow functions introduced in ES6. When using these alternatives, our event handler is already
automatically bound to the component instance, and we do not need to bind it in the constructor.

The reason is that in the case of arrow functions, this is bound lexically. This means that it uses the context of the
enclosing function — or global — scope as its this value.

# When 'Not' to Use Arrow Functions

https://dmitripavlutin.com/when-not-to-use-arrow-functions-in-javascript/

# react-redux

1. App.jsx

import { createStore } from 'redux'
import { Provider } from 'react-redux'
import reducer from './reducer'

const store = createStore(reducer);

class App extends React.Component {
  render() {
    return (
      <Provider store={store}>
        <div>
          {routing}
          <hr />
          <footer>
            <div style={{ textAlign: "center" }}>Footer</div>
          </footer>
        </div>
      </Provider>
    )
  }
}


This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.<br>
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.<br>
You will also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.<br>
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.<br>
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.<br>
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can’t go back!**

If you aren’t satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (Webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you’re on your own.

You don’t have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn’t feel obligated to use this feature. However we understand that this tool wouldn’t be useful if you couldn’t customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

### Code Splitting

This section has moved here: https://facebook.github.io/create-react-app/docs/code-splitting

### Analyzing the Bundle Size

This section has moved here: https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size

### Making a Progressive Web App

This section has moved here: https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app

### Advanced Configuration

This section has moved here: https://facebook.github.io/create-react-app/docs/advanced-configuration

### Deployment

This section has moved here: https://facebook.github.io/create-react-app/docs/deployment

### `npm run build` fails to minify

This section has moved here: https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify
