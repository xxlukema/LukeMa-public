import { createSelector } from 'reselect'

/**
 *  Note: selectors are usually created in a relevant reducer file or a separate selectors file.
 * 
 * https://medium.com/@pearlmcphee/selectors-react-redux-reselect-9ab984688dd4
 */
export const getCounter = (state) => state.fluxCounterReducer.count;

export const getEventText = (state) => state.fluxEventReducer.eventText;

/**
 * The biggest benefit of Reselect is that selectors created with the library are memoized and 
 * therefore will only re-run if their arguments change.
 */
export const getCounterReselect = createSelector(
    getCounter,
    /**
     * You can manipulate data, do calculation on data.
     */
    data => data);

export const getEventTextReselect = createSelector(
    getEventText,
    /**
     * You can manipulate data, do calculation on data.
     */
    data => {
        return data + ' / ' + data.toUpperCase();
    }
);

