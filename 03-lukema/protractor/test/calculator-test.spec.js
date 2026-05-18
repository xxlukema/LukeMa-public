describe('Protractor Demo App', function () {
  it('should have a title', function () {
    browser.get('http://juliemr.github.io/protractor-demo/');
    expect(browser.getTitle()).toEqual('Super Calculator');
  });
});

xdescribe('Protractor Demo App', function () {
  it('should have a title too', function () {
    browser.get('http://juliemr.github.io/protractor-demo/');
    expect(browser.getTitle()).not.toEqual('Super Calculatori 2');
  });
});

describe('Protractor Demo App', function () {
  it('should add one and two', function () {
    browser.get('http://juliemr.github.io/protractor-demo/');
    element(by.model('first')).sendKeys(1);
    element(by.model('second')).sendKeys(2);

    element(by.id('gobutton')).click();

    expect(element(by.binding('latest')).getText()).toEqual('3'); // This is wrong!
  });
});

describe('Protractor Demo App', function () {
  const firstNumber = element(by.model('first'));
  const secondNumber = element(by.model('second'));
  const goButton = element(by.id('gobutton'));
  const latestResult = element(by.binding('latest'));

  beforeEach(function () {
    browser.get('http://juliemr.github.io/protractor-demo/');
  });

  it('should have a title', function () {
    expect(browser.getTitle()).toEqual('Super Calculator');
  });

  it('should add one and two', function () {
    firstNumber.sendKeys(1);
    secondNumber.sendKeys(2);

    goButton.click();

    expect(latestResult.getText()).toEqual('3');
  });

  it('should add four and six', function () {
    firstNumber.sendKeys(4);
    secondNumber.sendKeys(6);

    goButton.click();

    expect(latestResult.getText()).toEqual('10');
  });

  it('should read the value from an input', function () {
    firstNumber.sendKeys(1);
    expect(firstNumber.getAttribute('value')).toEqual('1');
  });
});

describe('Protractor Demo App', function () {
  const firstNumber = element(by.model('first'));
  const secondNumber = element(by.model('second'));
  const goButton = element(by.id('gobutton'));
  const latestResult = element(by.binding('latest'));
  const history = element.all(by.repeater('result in memory'));

  function add(a, b) {
    firstNumber.sendKeys(a);
    secondNumber.sendKeys(b);
    goButton.click();
  }

  beforeEach(function () {
    browser.get('http://juliemr.github.io/protractor-demo/');
  });

  it('should have a history', function () {
    add(1, 2);
    add(3, 4);

    expect(history.count()).toEqual(2);

    add(5, 6);

    expect(history.count()).toEqual(3);
  });
});

/**
 * async / await
 */
/*
describe('angularjs homepage', function () {
  it('should greet the named user', async function () {
    debugger;
    await browser.get('http://www.angularjs.org');

    await element(by.model('yourName')).sendKeys('Julie');

    const greeting = element(by.binding('yourName'));

    expect(await greeting.getText()).toEqual('Hello Julie!');
  });

  describe('todo list', function () {
    let todoList;

    beforeEach(async function () {
      await browser.get('http://www.angularjs.org');
      todoList = element.all(by.repeater('todo in todoList.todos'));
    });

    it('should list todos', async function () {
      expect(await todoList.count()).toEqual(2);
      expect(await todoList.get(1).getText()).toEqual('build an AngularJS app');
    });

    it('should add a todo', async function () {
      const addTodo = element(by.model('todoList.todoText'));
      const addButton = element(by.css('[value="add"]'));

      await addTodo.sendKeys('write a protractor test');
      await addButton.click();

      expect(await todoList.count()).toEqual(3);
      expect(await todoList.get(2).getText()).toEqual('write a protractor test');
    });
  });
});
*/

fdescribe('angularjs homepage', function () {
  it('should greet the named user', function () {
    // Load the AngularJS homepage.
    browser.get('http://www.angularjs.org');

    // Find the element with ng-model matching 'yourName' - this will
    // find the <input type="text" ng-model="yourName"/> element - and then
    // type 'Julie' into it.
    element(by.model('yourName')).sendKeys('Julie');

    // Find the element with binding matching 'yourName' - this will
    // find the <h1>Hello {{yourName}}!</h1> element.
    var greeting = element(by.binding('yourName'));

    // Assert that the text element has the expected value.
    // Protractor patches 'expect' to understand promises.

    expect(greeting.getText()).toEqual('Hello Julie!');
  });
});
