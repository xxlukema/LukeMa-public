/**
 * Keyword: this
 * http://javascriptissexy.com/understand-javascripts-this-with-clarity-and-master-it/
 */


'use strict';



/**
 * 1. In object literal:
 */
var person = {
  firstName: 'Luke',
  lastName: 'Ma',
  fullName: function () {

    // Notice we use "this" just as we used "he" in the example sentence earlier?:
    console.log('1:' + this.firstName + ' ' + this.lastName);

    // We could have also written this:
    console.log('2: ' + person.firstName + ' ' + person.lastName);

    // Without this or object name:
    try {
      console.log('3: ' + firstName + ' ' + lastName);
    } catch (err) {
      console.log('4: Error: ' + err.message);
    }
  }
};

/**
 * Since the "this" keyword is used inside the showFullName method below, and the
 * showFullName method is defined on the person object,
 * "this" will have the value of the person object because the person object will invoke showFullName ()
 */
person.fullName();


/**
 *
 * $(this) will have the value of the button ($("button")) object
 * because the button object invokes the click () method:
 *
 * $("button").click (function (event) {
 *     console.log ($(this).prop("name"));
 * });
 *
 * The use of $(this) is jQuery's syntax for the this keyword in JavaScript.
 * jQuery library binds $(this) to the object that invokes the click method.
 *
 * Note that the button is a DOM element on the HTML page, and it is also an
 * object; in this case it is a jQuery object because we wrapped it in the jQuery $() function.
 *
 * "this" is not assigned a value until an object invokes the function where "this" is defined.
 *
 * In the global scope, when the code is executing in the browser, all global variables and
 * functions are defined on the window object. Therefore, when we use this in a global function,
 * it refers to (and has the value of) the global window object (not in strict mode though, as
 * noted earlier) that is the main container of the entire JavaScript application or web page.
 *
 *
 */


var firstName = 'Peter';
var lastName = 'Ally';

function showFullName() {
  // "this" inside this function will have the value of the window object
  // because the showFullName () function is defined in the global scope, just like the firstName and lastName
  try {
    console.log('5: ' + this.firstName + ' ' + this.lastName);
  } catch (err) {
    console.log('6: Error: ' + err.message);
  }
}

var person2 = {
  firstName: 'Penelope',
  lastName: 'Barrymore',
  showFullName: function () {
    // "this" on the line below refers to the person object, because the showFullName function will be invoked by person object.
    console.log('7: ' + this.firstName + ' ' + this.lastName);
  }
};

showFullName(); // Peter Ally

// window is the object that all global variables and functions are defined on, hence:
try {
  window.showFullName(); // Peter Ally
} catch (err) {
  console.log('7: Error: ' + err.message);
}

// "this" inside the showFullName () method that is defined inside the person object still refers to the person object, hence:
person2.showFullName(); // Penelope Barrymore


// If we invoke showFullName with a different object:
var anotherPerson = {
  firstName: 'Rohit',
  lastName: 'Khan'
};

// We can use the apply method to set the "this" value explicitly more on the apply() method later.
// "this" gets the value of whichever object invokes the "this" Function, hence:
try {
  person.showFullName.apply(anotherPerson); // Rohit Khan
} catch (err) {
  console.log('9: Error: ' + err.message);
}
// So the context is now anotherPerson because anotherPerson invoked the person.showFullName()  method by virtue of using the apply () method

// We have a simple object with a clickHandler method that we want to use when a button on the page is clicked
var user = {
  data: [
    { name: 'T. Woods', age: 37 },
    { name: 'P. Mickelson', age: 43 }
  ],
  clickHandler: function (event) {
    var randomNum = ((Math.random() * 2 | 0) + 1) - 1; // random number between 0 and 1

    // This line is printing a random person's name and age from the data array
    console.log(this.data[randomNum].name + ' ' + this.data[randomNum].age);
  }
};

/**
 * 1. Fix this when used in a method passed as a callback
 *
 * The button is wrapped inside a jQuery $ wrapper, so it is now a jQuery object
 * And the output will be undefined because there is no data property on the button object
 * $("button").click(user.clickHandler); // Cannot read property '0' of undefined
 * fix above line to:
 * $("button").click (user.clickHandler.bind (user)); // P. Mickelson 43
 *
 * http://javascriptissexy.com/javascript-apply-call-and-bind-methods-are-essential-for-javascript-professionals/
 *
 * 2. Fix this inside closure
 *
 *
 *
 */

var user = {
  tournament: 'The Masters',
  data: [
    { name: 'T. Woods', age: 37 },
    { name: 'P. Mickelson', age: 43 }
  ],

  clickHandler: function () {
    // the use of this.data here is fine, because "this" refers to the user object, and data is a property on the user object.

    this.data.forEach(function (person) {
      // But here inside the anonymous function (that we pass to the forEach method), "this" no longer refers to the user object.
      // This inner function cannot access the outer function's "this"

      console.log('What is This referring to? ' + this); // [object Window]

      console.log(person.name + ' is playing at ' + this.tournament);
      // T. Woods is playing at undefined
      // P. Mickelson is playing at undefined
    });
  }

};

user.clickHandler(); // What is "this" referring to? [object Window]

var user = {
  tournament: 'The Masters',
  data: [
    { name: 'T. Woods', age: 37 },
    { name: 'P. Mickelson', age: 43 }
  ],

  clickHandler: function (event) {
    // To capture the value of "this" when it refers to the user object, we have to set it to another variable here:
    // We set the value of "this" to theUserObj variable, so we can use it later
    var theUserObj = this;
    this.data.forEach(function (person) {
      // Instead of using this.tournament, we now use theUserObj.tournament
      console.log(person.name + ' is playing at ' + theUserObj.tournament);
    });
  }

};

user.clickHandler();
// T. Woods is playing at The Masters
//  P. Mickelson is playing at The Masters


/**
 * Fix this when method is assigned to a variable
 */
// This data variable is a global variable
var data = [
  { name: 'Samantha', age: 12 },
  { name: 'Alexis', age: 14 }
];

var user = {
  // this data variable is a property on the user object
  data: [
    { name: 'T. Woods', age: 37 },
    { name: 'P. Mickelson', age: 43 }
  ],
  showData: function (event) {
    var randomNum = ((Math.random() * 2 | 0) + 1) - 1; // random number between 0 and 1

    // This line is adding a random person from the data array to the text field
    console.log(this.data[randomNum].name + ' ' + this.data[randomNum].age);
  }

};

// Assign the user.showData to a variable
var showUserData = user.showData.bind(user);

// When we execute the showUserData function, the values printed to the console are from the global data array, not from the data array in the user object
//
showUserData(); // Samantha 12 (from the global data array)


/**
 * 4. Fix this when borrowing methods
 */

// We have two objects. One of them has a method called avg () that the other doesn't have
// So we will borrow the (avg()) method
var gameController = {
  scores: [20, 34, 55, 46, 77],
  avgScore: null,
  players: [
    { name: 'Tommy', playerID: 987, age: 23 },
    { name: 'Pau', playerID: 87, age: 33 }
  ]
};

var appController = {
  scores: [900, 845, 809, 950],
  avgScore: null,
  avg: function () {

    var sumOfScores = this.scores.reduce(function (prev, cur, index, array) {
      return prev + cur;
    });

    this.avgScore = sumOfScores / this.scores.length;
  }
};

// If we run the code below,
// the gameController.avgScore property will be set to the average score from the appController object "scores" array

// Don't run this code, for it is just for illustration; we want the appController.avgScore to remain null
// gameController.avgScore = appController.avg();
appController.avg.apply(gameController, gameController.scores);

