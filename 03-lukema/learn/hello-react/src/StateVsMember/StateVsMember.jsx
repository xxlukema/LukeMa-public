import React from 'react';

/**
 * State vs Member data
 * 
 * Both state and member data can be used. However, the view will be updated on state changes, not memeber data changes.
 * Therefore, if member data is updated, 
 *    this.setState({});
 * must be called to update the view data.
 * 
 * Also, react/no-direct-mutation-state. If
 *    this.state.counter = this.state.counter - 1; (violation of react/no-direct-mutation-state)
 * is called instead of
 *    this.setState({ counter: this.state.counter - 1 });
 * view will not be updated, unless
 *    this.setState({});
 * is called.
 */
class StateVsMember extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            header: "Header from state...",
            content: "Content from state...",
            counter: 10
        }

        this.increment = this.increment.bind(this);
        this.decrement = this.decrement.bind(this);
    }

    memberCounter = 20;

    increment() {
        /**
         * To change state data, use 'this.setState({ ... });'
         */
        this.setState({ counter: this.state.counter + 1 });
        this.memberCounter = this.memberCounter + 1;

        console.log('increment', this.state, this.memberCounter);
    }

    decrement() {
        /**
         * This will cause warning of: Do not mutate state directly. Use setState()
         * 
         * If change state data using 'this.state.counter = this.state.counter - 1;',
         * 'this.setState({});' must be invoked explicitly as the last statement in the function.
         */
        // eslint-disable-next-line
        this.state.counter = this.state.counter - 1;
        /**
         * Member data is not supported well. Memebr data is not watched to update view. State data is not watched either.
         * Only explicit call of 'this.setState({});' will trigger view update.
         */
        this.memberCounter--;
        /**
         * Explicitly call 'this.setState({});' to force view update for
         *    'this.state.counter = this.state.counter - 1;' and 'this.memberCounter--;'
         */
        this.setState({});

        console.log('decrement', this.state, this.memberCounter);
    }

    render() {
        return (
            <div>
                <h1>{this.state.header}</h1>
                <h2>{this.state.content}</h2>

                <div>
                    <button onClick={this.decrement}>Decrement</button>
                    <span style={{ margin: 20, fontWeight: "bold" }}>State Counter: {this.state.counter}</span>
                    <span style={{ margin: 20, fontWeight: "bold" }}>memberCounter: {this.memberCounter}</span>
                    <button onClick={this.increment}>Increment</button>
                </div>

            </div>
        );
    }
}

export default StateVsMember;