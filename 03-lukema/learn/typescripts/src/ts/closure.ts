
/**
 * A function remembers the variables during the time of
 * declaration, no matter where the function is called. That
 * is closure.
 * 
 */


const a = 'a';

function outer() {
    const b = 'b';

    function inner() {
        console.log(a);
        console.log(b);
    }

    inner();

    const inner2 = () => {
        console.log('A', a);
        console.log('B', b);
    }

    /**
     * Cannot use this.
     */
    // return this.inner2;
    return inner2;
}

outer();

const inner2 = outer();
inner2();

