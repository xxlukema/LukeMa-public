# `spyOn`

In Jasmine, mocks are referred to as spies. There are two ways to create a spy in Jasmine: `spyOn()` can only be used when the method already
exists on the object, whereas `jasmine.createSpy()` will return a brand new function:

```ts
// `spyOn(object, methodName)` where `object.method()` is a function
spyOn(obj, 'myMethod')
```

```ts
// jasmine.createSpy(stubName);
var myMockMethod = jasmine.createSpy('My Method');
```

As we'll soon see, both of the above methods have their place in your unit tests.
Using the spyOn() Method

As mentioned above, spyOn() can only be used when the method already exists on the object. For simple tests, this is your best bet.
Our test cases all feature the following Person object. It has a couple of attributes, a getter and setter for the name, and two public methods:

```ts
var Person = function() { 
    //defaults
    var _age  =  0,
        _name = 'John Doe';
 
    this.initialize = function(name, age) {
      _name = name || _name;
      _age  = age  || _age;
    };
    if (arguments.length) this.initialize();
      
    //getters and setters
    this.getName     = function()      { return _name; };
    this.setName     = function (name) { _name = name; };
 
    //public methods
    this.addBirthday = function()      { _age++; };
    this.toString    = function()      { return 'My name is " + this.getName() + " and I am " + _age + " years old.'; };
};
```

Say that we want to verify that the `toString()` method was calling `getName()`. We would instantiate the Person as usual, but before calling toString(),
we would call spyOn(), passing in the person instance and the name of the method that we want to spy on ('getName'). We can then call jasmine matchers
to see what happened. The simplest test is to check that getName() was in fact called:

```ts
describe("Person toString() Test", function() {
    it("calls the getName() function", function() {
        var testPerson = new Person();
        spyOn(testPerson, "getName");
        testPerson.toString();
        expect(testPerson.getName).toHaveBeenCalled();
    });
});
```

But that's just the beginning. We can run other tests on our spied function, such as what arguments it was called with. The toHaveBeenCalledWith()
method accepts a value to be compared against the method's arguments attribute. Conversely, we can test that the function was called without any
parameters by calling toHaveBeenCalledWith() without a value:

```ts
describe("Person toString() Test", function() {
    var testPerson;
    beforeEach(function() { testPerson = new Person(); });   
    afterEach (function() { testPerson = undefined;    });
     
    it("calls the getName() function", function() {
        spyOn(testPerson, "getName");
        testPerson.toString();
        expect(testPerson.getName).toHaveBeenCalled();
    });
     
    it("Method getName() was called with zero arguments", function() {
        // Ensure the spy was called with the correct number of arguments
        // In this case, no arguments
        expect(testPerson.getName).toHaveBeenCalledWith();
        // this also works
        // expect(testPerson.getName.mostRecentCall.args.length).toEqual(0);
    });
});
```

Creating Our Own Spy Method

Sometimes, it may be beneficial to completely replace the original method with a fake one for testing. Perhaps the original method takes a long time to
execute, or it depends on other objects that aren't available in the test context. Jasmine lets us handle this issue by creating a fake method using
jasmine.createSpy(). Here's how to substitute a fake getName() for the real one:

```ts
describe("Person toString() Test with Fake getName() Method", function() {
    it("calls the fake getName() function", function() {
        var testPerson = new Person();
        testPerson.getName = jasmine.createSpy("getName spy");
        testPerson.toString();
        expect(testPerson.getName).toHaveBeenCalled();
    });
});
```

Unlike spyOn(), creating a fake method circumvents the original method so that it is not called during tests. Thus, the alert in getName() below will not appear:

```ts
var Person = function() { 
    //...
    this.getName = function() { 
      alert("You called?");  //won't be called
      return _name; 
    };
    //...
}; 
 
describe("Person toString() Test with Fake getName() Method", function() {
    it("calls the fake getName() function", function() {
        var testPerson = new Person();
        testPerson.getName = jasmine.createSpy("getName() spy");
        testPerson.toString();
        expect(testPerson.getName).toHaveBeenCalled();
    });
});
```

Modifying the Fake Method

If your method is being called by another method, you may want it to return something. You can tell Jasmine what to return using the andReturn(value) method:

```ts
testPerson.getName = jasmine.createSpy("getName() spy").andReturn("Bobby");
```

And finally, here's a way to substitute an entirely different method body for the original:

```ts
// defining a spy on an existing property: testPerson.getName() calls an anonymous function
testPerson.getName = jasmine.createSpy("getName() spy").andCallFake(function() {
    console.log("Hello from getName()");
    return "Bobby";
});
```

The above function not only returns "Bobby" each time, but it also logs a message to the console. That would be a little harder to do with the original function.
