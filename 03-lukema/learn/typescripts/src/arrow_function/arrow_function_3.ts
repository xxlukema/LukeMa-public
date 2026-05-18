
// 'use strict';

const globalName = 'Global Name';

const myObject = {
    name: 'Luke Ma',
    myFunc: function () {
        console.log('myFunc1', this.name, globalName);
        console.log('myFunc2', this);
    },
    myArrow: () => {
        console.log('myArrow1', globalName);
        console.log('myArrow2', this);
    }
}

console.log('----------- 1 -----------');
myObject.myFunc();

console.log('----------- 2 -----------');
myObject.myArrow();

console.log('----------- 3 -----------');
console.log('Global', this);

const MyCaller = {
    call() {
        console.log('----------- 4 -----------');
        myObject.myFunc();

        console.log('----------- 5 -----------');
        myObject.myArrow();
    }
}

console.log('----------- 6 -----------');
MyCaller.call();

class MyClass {

    constructor(private name: string) {
        console.log('MyClass constructor.');
    }

    myFunc2() {
        console.log('myFunc2', this.name, this);
    }

    myArrow2 = () => {
        console.log('myArrow2', this.name, this);
    }
}

const myClass = new MyClass('Hello MyClass');
myClass.myFunc2();
myClass.myArrow2();

const MyCaller2 = {
    call() {
        console.log('----------- 6 -----------');
        myClass.myFunc2();

        console.log('----------- 7 -----------');
        myClass.myArrow2();
    }
}

MyCaller2.call();
