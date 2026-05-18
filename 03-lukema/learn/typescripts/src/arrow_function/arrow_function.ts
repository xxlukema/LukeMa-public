'use strict';


const test = {
	name: 'test object',
	createAnonFunction: function () {
		console.log(this.name);
		console.log(arguments);
	},

	createArrowFunction: function () {
		return () => {
			console.log(this.name);
			// console.log(arguments);
		};
	}
};

const anon = test.createAnonFunction();
const arrow = test.createArrowFunction()();
