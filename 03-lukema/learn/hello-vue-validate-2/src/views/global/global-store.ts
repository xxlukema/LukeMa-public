export const GlobalStore = {
    state: {
        counter: 8
    },

    increment() {
        this.state.counter++;
        
        console.log('increment', this.state);
    },

    decrement() {
        this.state.counter--;
        
        console.log('decrement', this.state);
    }
};
