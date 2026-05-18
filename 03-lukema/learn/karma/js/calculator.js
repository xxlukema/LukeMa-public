'use strict';

window.calculator = (function () {

  return {
    init: init
  }

  function getIntById(id) {
    return parseInt(document.getElementById(id).value, 10);
  };

  function calculate() {
    const sum = getIntById('x') + getIntById('y');
    document.getElementById('result').innerHTML = isNaN(sum) ? 'check invalid inputs' : sum;
  };

  function init() {
    document.getElementById('add').addEventListener('click', calculate);
  };

})();
