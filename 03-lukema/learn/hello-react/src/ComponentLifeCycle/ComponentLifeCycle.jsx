import React from 'react';

class ComponentLifeCycle extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            data: 0
        }
        this.setNewNumber = this.setNewNumber.bind(this)
    }

    setNewNumber() {
        this.setState({ data: this.state.data + 1 })
    }

    render() {
        return (
            <div>
                <button onClick={this.setNewNumber}>Increment</button>
                <Content myNumber={this.state.data}></Content>
            </div>
        );
    }
}

class Content extends React.Component {
    /**
     * https://reactjs.org/blog/2018/03/27/update-on-async-rendering.html
     * 16.3: Renamed to UNSAFE_componentWillMount
     * 17.0: Remove componentWillMount, componentWillReceiveProps, and componentWillUpdate . 
     *       (Only the new “UNSAFE_” lifecycle names will work from this point forward.)
     */
    /*
    componentWillMount() {
        console.log('Component WILL MOUNT!')
    }
    */
    componentDidMount() {
        console.log('Component DID MOUNT!')
    }
    /*
    componentWillReceiveProps(newProps) {
        console.log('Component WILL RECIEVE PROPS!')
    }
    */
    shouldComponentUpdate(newProps, newState) {
        return true;
    }
    /*
    componentWillUpdate(nextProps, nextState) {
        console.log('Component WILL UPDATE!');
    }
    */
    componentDidUpdate(prevProps, prevState) {
        console.log('Component DID UPDATE!')
    }
    componentWillUnmount() {
        console.log('Component WILL UNMOUNT!')
    }
    render() {
        return (
            <div>
                <h3>{this.props.myNumber}</h3>
            </div>
        );
    }
}

export default ComponentLifeCycle;