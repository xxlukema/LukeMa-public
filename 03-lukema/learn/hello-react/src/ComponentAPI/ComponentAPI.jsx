import React from 'react';
import ReactDOM from 'react-dom';

class ComponentAPI extends React.Component {
    constructor() {
        super();

        this.state = {
            data: [],
            colors: ['lightgray', 'lightgreen']
        }

        /**
         * In new ES6 classes, we have to manually bind this. We will use this.method.bind(this)
         */
        this.setStateHandler = this.setStateHandler.bind(this);
        this.forceUpdateHandler = this.forceUpdateHandler.bind(this);
        this.findDomNodeHandler = this.findDomNodeHandler.bind(this);
    }

    findDomNodeHandler() {
        var myDiv = document.getElementById('myDiv');

        if (ReactDOM.findDOMNode(myDiv).style.backgroundColor === this.state.colors[0]) {
            ReactDOM.findDOMNode(myDiv).style.backgroundColor = this.state.colors[1];
        } else {
            ReactDOM.findDOMNode(myDiv).style.backgroundColor = this.state.colors[0];
        }
    }

    setStateHandler() {
        const item = "setState..."
        const myArray = this.state.data.slice();
        myArray.push(item);
        this.setState({ data: myArray })
    }

    forceUpdateHandler() {
        this.forceUpdate();
    }

    render() {
        return (
            <div>
                <div>
                    <h3>setState()</h3>
                    <button onClick={this.setStateHandler}>SET STATE</button>
                    <h4>State Array: {this.state.data}</h4>
                </div>

                <div>
                    <h3>forceUpdate()</h3>
                    <button onClick={this.forceUpdateHandler}>FORCE UPDATE</button>
                    <h4>Random number: {Math.random()}</h4>
                </div>

                <div>
                    <h3>ReactDOM.findDOMNode()</h3>
                    <button onClick={this.findDomNodeHandler}>FIND DOME NODE</button>
                    <div id="myDiv">NODE</div>
                </div>
            </div>
        );
    }
}

export default ComponentAPI;