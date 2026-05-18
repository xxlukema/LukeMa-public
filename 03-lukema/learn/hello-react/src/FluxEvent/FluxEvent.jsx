import React from 'react';
import { connect } from 'react-redux';
import { changed, cleanText, keyup } from './FluxEventActions';
import { getCounterReselect, getEventTextReselect } from '../Flux/selector/Selectors';


/**
 * Function mapStateToProps (the first function you pass to connect) gets passed the whole store as parameter,
 * its job is to map specific parts of the state to the component. Only what is returned from mapStateToProps
 * will be mapped as a prop to your component.
 */
const mapStateToProps = (state) => {
    console.log('mapStateToProps', state, state.fluxEventReducer);

    return {
        propEventText: getEventTextReselect(state)
    };
};

const mapStateToPropsCounter = (state) => {
    console.log('mapStateToPropsCounter', state, state.fluxEventReducer);

    return {
        propCounter: getCounterReselect(state)
    };
};

/**
 * mapDispatchToProps that receives the dispatch method and returns callback props that you want to inject
 * into the container component. We will pass 'propOnClickAction' as props to <Button > component.
 * 
 */
const mapDispatchToPropsClearText = (dispatch) => (
    {
        propOnClickClearText: () => { dispatch(cleanText()) },
    }
);

const mapDispatchToPropsKeyup = (dispatch) => (
    {
        propOnClickKeyup: () => { dispatch(keyup()) },
    }
);

const mapDispatchToPropsChanged = (dispatch) => (
    {
        propOnClickChanged: () => { dispatch(changed()) }
    }
);

let CleanTextEvent = ({ propOnClickClearText }) => (
    <button onClick={propOnClickClearText}>Clear Text</button>
);

CleanTextEvent = connect(mapStateToProps, mapDispatchToPropsClearText)(CleanTextEvent);

let KeyupEvent = ({ propOnClickKeyup }) => (
    <button style={{ marginLeft: 10, marginRight: 10 }} onClick={propOnClickKeyup}>Keyup</button>
);

KeyupEvent = connect(mapStateToProps, mapDispatchToPropsKeyup)(KeyupEvent);

let ChangedEvent = ({ propOnClickChanged }) => (
    <button onClick={propOnClickChanged}>Changed</button>
);

ChangedEvent = connect(mapStateToProps, mapDispatchToPropsChanged)(ChangedEvent);

let DisplayEventArea = ({ propEventText }) => (
    <section>
        <div>Event Text: <span style={{ fontWeight: "bold" }}>{propEventText}</span></div>
    </section>
);

DisplayEventArea = connect(mapStateToProps)(DisplayEventArea);

let DisplayCounterArea = ({ propCounter }) => (
    <section>
        <div>Counter: <span style={{ fontWeight: "bold" }}>{propCounter}</span></div>
    </section>
);

DisplayCounterArea = connect(mapStateToPropsCounter)(DisplayCounterArea);


class FluxEvent extends React.Component {

    componentDidMount() {
        console.log('Component DID MOUNT!')
    }

    render() {
        return (
            <div>
                <h3>FluxEvent</h3>

                <CleanTextEvent></CleanTextEvent>
                <KeyupEvent></KeyupEvent>
                <ChangedEvent></ChangedEvent>
                <br />
                <br />
                <DisplayEventArea></DisplayEventArea>
                <DisplayCounterArea></DisplayCounterArea>
            </div>
        );
    }
}

export default FluxEvent;