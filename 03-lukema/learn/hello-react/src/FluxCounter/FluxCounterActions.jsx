import { DECREMENT, INCREMENT } from './FluxCounterActionTypes';

export const increment = () => {
    return {
        type: INCREMENT
    };
}

export const decrement = () => ({
    type: DECREMENT
});
