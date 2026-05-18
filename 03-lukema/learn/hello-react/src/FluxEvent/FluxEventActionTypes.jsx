/**
 * Actions are plain JavaScript objects. 
 * 
 * Actions must have a "type" property that indicates the type of action being performed. 
 * Types should typically be defined as string constants. 
 * 
 * Once your app is large enough, you may want to move them into a separate module.
 */

/*
 * action types
 */
export const CLEAN_TEXT = 'CLEAN_TEXT';
export const KEYUP = 'KEYUP';
export const INPUT_CHANGED = 'INPUT_CHANGED';
