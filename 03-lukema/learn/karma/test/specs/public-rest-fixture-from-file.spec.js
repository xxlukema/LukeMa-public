'use strict';

/**
 * Unit tests for js/public-rest.js
 */


describe('public-rest with offline fixture partial', function () {

  // inject the HTML fixture for the tests
  beforeEach(function () {
    /**
     * 'base/' is manditory
     * 
     * jasmine.getFixtures().load('public-rest.fixture.html');
     */
    jasmine.getFixtures().fixturesPath = 'base/test/fixtures';
    loadFixtures('public-rest.fixture.html');

  });

  // remove the html fixture from the DOM
  afterEach(function () {
    jasmine.getFixtures().cleanUp();
  });

  it('should public-rest return json object', function () {

    loadTemplate();
    bindData();
    localStorageReady();
    sessionStorageReady();

    $('#method').val('posts');
    $('#id').val(1);
    $('#getState').click();

    console.log($('#method').val() + ' <=====> ' + $('#id').val() + ' button value: ' + $('#getState').val() + ' result: ' + $('#result').text());

    expect($('#result').text()).toBe('1 json-server typicode');
  });

});
