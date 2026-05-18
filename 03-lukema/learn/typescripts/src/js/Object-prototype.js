

/**
 * Prototype
 *
 * Nearly all objects in JavaScript are instances of Object. A typical object inherits
 * properties and methods from Object.prototype, although these properties may be
 * shadowed (a.k.a. overridden).
 *
 **/

var Person = function(name) {
  this.name = name;
  this.canTalk = true;
};

Person.prototype.greet = function() {
  if (this.canTalk) {
    console.log('Hi, I am ' + this.name);
  } else {
    console.log('Hi, I cannot talk: ' + this.name);
  }
};

var Employee = function(name, title) {
  Person.call(this, name);
  this.title = title;
};

Employee.prototype = Object.create(Person.prototype);
Employee.prototype.constructor = Employee;

Employee.prototype.greet = function() {
  if (this.canTalk) {
    console.log('Hi, I am ' + this.name + ', the ' + this.title);
  } else {
    console.log('Hi, I cannot talk: ' + this.name + ', the ' + this.title);
  }
};

var Customer = function(name) {
  Person.call(this, name);
};

Customer.prototype = Object.create(Person.prototype);
Customer.prototype.constructor = Customer;

var Mime = function(name) {
  Person.call(this, name);
  this.canTalk = false;
};

Mime.prototype = Object.create(Person.prototype);
Mime.prototype.constructor = Mime;

var bob = new Employee('Bob', 'Builder');
var joe = new Customer('Joe');
var rg = new Employee('Red Green', 'Handyman');
var mike = new Customer('Mike');
var mime = new Mime('Mime');


// Hi, I am Bob, the Builder
bob.greet();

// Hi, I am Joe
joe.greet();

// Hi, I am Red Green, the Handyman
rg.greet();

// Hi, I am Mike
mike.greet();

mime.greet();


var a = { a: 1 };

console.log(a);

console.log('a.a = ' + a.a);
console.log('a[a] = ' + a[a]);
console.log('a[\'a\'] = ' + a.a);

