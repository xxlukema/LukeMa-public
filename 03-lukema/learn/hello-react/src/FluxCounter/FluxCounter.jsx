import React from 'react';
import { connect } from 'react-redux';
import { increment, decrement } from './FluxCounterActions';
import { getCounterReselect } from '../Flux/selector/Selectors';

/**
 * Function mapStateToProps (the first function you pass to connect) gets passed the whole store as parameter,
 * its job is to map specific parts of the state to the component. Only what is returned from mapStateToProps
 * will be mapped as a prop to your component.
 */
const mapStateToProps = (state) => {
    console.log('mapStateToProps', state, state.fluxCounterReducer);

    return {
        propCounter: getCounterReselect(state)
    };
};

/**
 * mapDispatchToProps that receives the dispatch method and returns callback props that you want to inject
 * into the container component. We will pass 'propOnClickAction' as props to <Button > component.
 * 
 */
const mapDispatchToProps = (dispatch) => (
    {
        propOnClickIncrement: () => { dispatch(increment()) },
        propOnClickDecrement: () => { dispatch(decrement()) }
    }
);

let Button = ({ propCounter, propStateObject, propOnClickIncrement, propOnClickDecrement }) => (
    <div >
        <button onClick={propOnClickDecrement}>Decrement</button>
        <span style={{ margin: 20, fontWeight: "bold" }}>Counter: {propCounter}</span>
        <button onClick={propOnClickIncrement}>Increment</button>

        <div>Counter: {propCounter}</div>
    </div >
);

Button = connect(mapStateToProps, mapDispatchToProps)(Button);

class FluxCounter extends React.Component {
    render() {
        return (
            <div>
                <h1>FluxCounter</h1>
                <Button></Button>
            </div>
        )
    }
}

export default FluxCounter;