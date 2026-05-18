
import { CLEAN_TEXT, KEYUP, INPUT_CHANGED, } from '../FluxEvent/FluxEventActionTypes';

const initialState = {
    eventText: 'Click on the event emitters...'
};

/**
 * function reducer(state = initialState, action) {}
 */
const FluxEventReducer = (state = initialState, action) => {
    console.log('FluxEventReducer', state, action);

    switch (action.type) {
        case CLEAN_TEXT:
            return {
                ...state,
                eventText: 'clean-text'
            };
        case KEYUP:
            return {
                ...state,
                eventText: 'keyup'
            };
        case INPUT_CHANGED:
            return {
                ...state,
                eventText: 'changed'
            };
        default:
            return state;
    }

};

export default FluxEventReducer;