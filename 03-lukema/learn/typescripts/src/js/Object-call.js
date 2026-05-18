
/**
 * Function.prototype.call()
 * The call() method calls a function with a given this value and arguments provided individually.
 * Return valu: The result of calling the function with the specified this value and arguments.
 *
 **/

function Product(name, price) {
  this.name = name;
  this.price = price;
};

function Food(name, price) {
  Product.call(this, name, price);
  this.category = 'food';
};

function Toy(name, price) {
  Product.call(this, name, price);
  this.category = 'toy';
};

Product.prototype.toString = function() {
  var msg = 'name: ' + this.name + '. price: ' + this.price;

  if (this.category != undefined) {
    msg += '. category: ' + this.category;
  }

  return msg;
};

Food.prototype = Object.create(Product.prototype);

Toy.prototype = Object.create(Product.prototype);

var prod = new Product('Product', 0.99);
var cheese = new Food('feta', 5);
var fun = new Toy('robot', 40);

console.log(prod.toString());
console.log(cheese.toString());
console.log(fun.toString());

