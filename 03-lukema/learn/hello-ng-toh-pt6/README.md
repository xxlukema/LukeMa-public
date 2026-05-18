# TOH - Tour of Heros

[Tour of Heros]<https://angular.io/tutorial>

In the "Tour of Heros" page --> Solutions --> Click the link to download source code: "live example / download example"

[Download Tour of Heros]<https://angular.io/generated/zips/toh-pt6/toh-pt6.zip>

## `protractor` Tutorials

[Protractor Tutorials]<https://www.protractortest.org/#/tutorial>

[Protractor Tutorials - Table Of Contents]<https://www.protractortest.org/#/toc>

[Protractor Tutorials - Protractor API]<https://www.protractortest.org/#/api>


## Highlghts of [Protractor Tutorials]<https://www.protractortest.org/#/tutorial>

<pre>
// spec.js
describe('Protractor Demo App', function() {
  it('should have a title', function() {
    browser.get('http://juliemr.github.io/protractor-demo/');

    expect(browser.getTitle()).toEqual('Super Calculator');
  });
});

// conf.js
exports.config = {
  framework: 'jasmine',
  seleniumAddress: 'http://localhost:4444/wd/hub',
  specs: ['spec.js']
}

// spec.js
describe('Protractor Demo App', function() {
  it('should add one and two', function() {
    browser.get('http://juliemr.github.io/protractor-demo/');
    element(by.model('first')).sendKeys(1);
    element(by.model('second')).sendKeys(2);

    element(by.id('gobutton')).click();

    expect(element(by.binding('latest')).getText()).
        toEqual('5'); // This is wrong!
  });
});
</pre>

The `describe` and `it` syntax is from the Jasmine framework. `browser` is a global created by Protractor, which is used for browser-level
commands such as navigation with `browser.get`.

This uses the globals `element` and `by`, which are also created by Protractor. The `element` function is used for finding HTML elements on
your webpage. It returns an ElementFinder object, which can be used to interact with the element or get information from it. In this test, 
we use `sendKeys` to type into `<input>`s, click to click a button, and `getText` to return the content of an element.

element takes one parameter, a Locator, which describes how to find the element. The by object creates Locators. Here, we're using three types of Locators:

    by.model('first') to find the element with ng-model="first" in <input type="text" ng-model="first">.
    by.id('gobutton') to find the element with the given id. This finds <button id="gobutton">.
    by.binding('latest') to find the element bound to the variable latest. This finds the span containing {{latest}}


<pre>
// spec.js
describe('Protractor Demo App', function() {
  var firstNumber = element(by.model('first'));
  var secondNumber = element(by.model('second'));
  var goButton = element(by.id('gobutton'));
  var latestResult = element(by.binding('latest'));

  beforeEach(function() {
    browser.get('http://juliemr.github.io/protractor-demo/');
  });

  it('should have a title', function() {
    expect(browser.getTitle()).toEqual('Super Calculator');
  });

  it('should add one and two', function() {
    firstNumber.sendKeys(1);
    secondNumber.sendKeys(2);

    goButton.click();

    expect(latestResult.getText()).toEqual('3');
  });

  it('should add four and six', function() {
    // Fill this in.
    expect(latestResult.getText()).toEqual('10');
  });

  it('should read the value from an input', function() {
    firstNumber.sendKeys(1);
    expect(firstNumber.getAttribute('value')).toEqual('1');
  });
});
</pre>

Here, we've pulled the navigation out into a `beforeEach` function which is run before every it block. We've also stored the `ElementFinders` for 
the first and second input in nice variables that can be reused. Fill out the second test using those variables, and run the tests again to ensure they pass.

In the last assertion we read the value from the input field with `firstNumber.getAttribute('value')` and compare it with the value we have set before

<pre>
// conf.js
exports.config = {
  framework: 'jasmine',
  seleniumAddress: 'http://localhost:4444/wd/hub',
  specs: ['spec.js'],
  multiCapabilities: [{
    browserName: 'firefox'
  }, {
    browserName: 'chrome'
  }]
}
</pre>


