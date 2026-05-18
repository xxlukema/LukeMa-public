
import { INCREMENT, DECREMENT } from './FluxCounterActionTypes';

const initialState = {
    count: 8
};

/**
 * function reducer(state = initialState, action) {}
 */
const FluxCounterReducer = (state = initialState, action) => {
    console.log('FluxCounterReducer', state, action);

    switch (action.type) {
        case INCREMENT:
            console.log('FluxCounterReducer 1111');
            return {
                ...state,
                count: state.count + 1
            };
        case DECREMENT:
            console.log('FluxCounterReducer 2222');
            return {
                ...state,
                count: state.count - 1
            };
        default:
            console.log('FluxCounterReducer 3333');
            return state;
    }

};

export default FluxCounterReducer;