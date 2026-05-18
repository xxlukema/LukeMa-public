import React from 'react';

class Event extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            data1: 'Initial data...',
            data2: 'Initial data 222'
        }

        this.updateState1 = this.updateState1.bind(this);
        this.updateState2 = this.updateState2.bind(this);
    }

    updateState1() {
        this.setState({ data1: 'You clicked button 1...' })
    }

    updateState2() {
        this.setState({ data2: 'You clicked button 2...' })
    }

    render() {
        return (
            <div>
                <button onClick={this.updateState1}>CLICK 1</button>
                <h4>{this.state.data1}</h4>

                <br />

                <Content myDataProp={this.state.data2} updateStateProp={this.updateState2}></Content>
            </div>
        );
    }
}

class Content extends React.Component {
    render() {
        return (
            <div>
                {
                    /**
                     * Do not use '()'. Otherwise, it will cause infinite loop.
                     * 
                     * <button onClick={this.props.updateStateProp()}>CLICK 2</button> --- This is wrong
                     */
                }
                <button onClick={this.props.updateStateProp}>CLICK 2</button>
                <h3>{this.props.myDataProp}</h3>
            </div>
        );
    }
}

export default Event;