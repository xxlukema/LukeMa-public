'use strict';

/**
 * Unit tests for js/calculator.js
 */

describe('Calculator with inline fixture', function () {

  beforeEach(function () {
    const fixture = '<div id="fixture"><input id="x" type="text" value="123" />' +
      '<input id="y" type="text" />' +
      '<input id="add" type="button" value="Add Numbers" />' +
      'Result: <span id="result" /></div>';

    document.body.insertAdjacentHTML(
      'afterbegin',
      fixture);
  });

  afterEach(function () {
    document.body.removeChild(document.getElementById('fixture'));
  });

  beforeEach(function () {
    window.calculator.init();
  });

  it('should return 3 for 1 + 2', function () {
    $('#x').val(1);
    $('#y').val(2);
    $('#add').click();
    expect($('#result').text()).toBe('3');
    expect($('#result').html()).toBe('3');
  });

  it('should display invalid message for invalid x value', function () {
    $('#x').val('hello');
    $('#y').val(2);
    $('#add').click();
    expect($('#result').text()).toBe('check invalid inputs');
  });

});
