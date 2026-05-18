import { CLEAN_TEXT, INPUT_CHANGED, KEYUP } from './FluxEventActionTypes';

export const cleanText = () => {
    return {
        type: CLEAN_TEXT
    };
}

export const keyup = () => ({
    type: KEYUP
});

export const changed = () => ({
    type: INPUT_CHANGED
});
