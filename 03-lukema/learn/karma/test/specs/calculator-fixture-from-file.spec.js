'use strict';

/**
 * Unit tests for js/calculator.js
 */


describe('Calculator with offline fixture partial', function () {

  // API for interacting with the page.
  const controls = {

    get result() {
      return $('#result').text();
    },

    get x() {
      return $('#x').val();
    },

    set x(val) {
      $('#x').val(val);
    },

    get y() {
      return $('#y').val();
    },

    set y(val) {
      $('#y').val(val);
    },

    clickAdd: function () {
      $('#add').click();
    }
  };

  // inject the HTML fixture for the tests
  beforeEach(function () {
    /**
     * 'base/' is manditory
     * 
     * jasmine.getFixtures().load('calculator.fixture.html');
     */
    jasmine.getFixtures().fixturesPath = 'base/test/fixtures';
    loadFixtures('calculator.fixture.html');

    // init js lib
    window.calculator.init();
  });

  // remove the html fixture from the DOM
  afterEach(function () {
    jasmine.getFixtures().cleanUp();
  });

  it('should calculate 3 for 1 + 2', function () {
    controls.x = 11;
    controls.y = 2;
    controls.clickAdd();
    expect(controls.result).toBe('13');
    expect($('#result').text()).toBe('13');
  });

  it('should display invalid message for invalid x value', function () {
    controls.x = 'hello';
    controls.y = 2;
    controls.clickAdd();
    expect(controls.result).toBe('check invalid inputs');
  });

});
