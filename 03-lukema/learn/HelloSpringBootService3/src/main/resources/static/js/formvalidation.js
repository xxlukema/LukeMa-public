/*
 * jQuery Form Validation: 
 *    https://jqueryvalidation.org/documentation/
 *    https://jqueryvalidation.org/
 * 
 *    List of built-in Validation methods
 *       A set of standard validation methods is provided:
 * 
 *    required – Makes the element required.
 *    remote – Requests a resource to check the element for validity.
 *    minlength – Makes the element require a given minimum length.
 *    maxlength – Makes the element require a given maximum length.
 *    rangelength – Makes the element require a given value range.
 *    min – Makes the element require a given minimum.
 *    max – Makes the element require a given maximum.
 *    range – Makes the element require a given value range.
 *    step – Makes the element require a given step.
 *    email – Makes the element require a valid email
 *    url – Makes the element require a valid url
 *    date – Makes the element require a date.
 *    dateISO – Makes the element require an ISO date.
 *    number – Makes the element require a decimal number.
 *    digits – Makes the element require digits only.
 *    equalTo – Requires the element to be the same as another one
 * 
 */

// Wait for the DOM to be ready
$(function () {

  /** Begin: generic validation 1 of 3 */
  /*
	 * $("input[type='text'], textarea").keyup(function ($event) {
	 * addRemoveRedBorder($event); });
	 * 
	 * $("input[type='text'], textarea").change(function ($event) {
	 * addRemoveRedBorder($event); });
	 * 
	 * $("input[type='text'], textarea").focusout(function ($event) {
	 * addRemoveRedBorder($event); });
	 * 
	 * $('select').change(function ($event) { addRemoveRedBorder($event); });
	 */

  $.validator.addMethod('noneBlank', function (value, element) {

    if (this.optional(element)) {
      return false;
    }

    if (typeof value === 'undefined' || value == null) {
      return false;
    }

    return value.trim().length > 0;
  },
    'Blank string is not a valid input.');
  /** End: generic validation 1 of 3 */

  $.validator.addMethod("adjustAmt", function (value, element) {

    if (this.optional(element)) {
      return false;
    }

    if (typeof value === 'undefined' || value == null) {
      return false;
    }

    return value > 0;
  },
    'Positive number is required.');

  /** Begin: generic validation 2 of 3 */
  const validator = $("form[name='registration']").validate({
	/** If debug: false, the submitHandler() will not be invoked. */
    debug: false,
    ignore: '.ignore',
    errorClass: 'label-form-input-error',
    highlight: function (element, errorClass, validClass) {
      $(element).addClass('indi-form__input--has-error');
    },
    unhighlight: function (element, errorClass, validClass) {
      $(element).removeClass('indi-form__input--has-error');
    },
    rules: {
      desc: {
        noneBlank: true,
        /* This has higher priority than attribute in element */
        required: true
      },
      firstname: 'required',
      lastname: 'required',
      email: {
        required: true,
        email: true
      },
      password: {
        required: true,
        minlength: 5
      }
    },
    messages: {
      desc: {
        noneBlank: 'Please enter a none blank string 1',
        required: 'Please enter a none blank string 2'
      },
      firstname: 'Please enter your firstname',
      lastname: 'Please enter your lastname',
      password: {
        required: 'Please provide a password',
        minlength: 'Your password must be at least 5 characters long'
      },
      email: 'Please enter a valid email address'
    },
    submitHandler: function (form) {
      console.log('Form validation successful.');
      // form.submit();
    },
    invalidHandler: function (event, validator) {
      // 'this' refers to the form
      const errors = validator.numberOfInvalids();
      console.log('Form valid failed. Invalid form. errors=' + errors);

		 // for (let i = 0; i < validator.errorList.length; i++) {
		 // console.log(validator.errorList[i]);
		 // validator.errorList[i].element.classList.add('indi-form__input--has-error');
			// }
		 // 
		 // //validator.errorMap is an object mapping input names -> error
		 // messages for (let i in validator.errorMap) { console.log(i, ':',
		 // validator.errorMap[i]); }
    }
  });
  /** End: generic validation 2 of 3 */

  /** Begin: generic validation 3 of 3 */
  $("input[type='reset']").on('click', function ($event) {
    console.log('Reset clicked.');
    validator.resetForm();
  });
  /** End: generic validation 3 of 3 */

  $(".row-test").click(function ($event) {
    let value = $event.target.value;
    console.log('value = ' + value);

    let found = false;
    $("table[name='table-test'] > tbody > tr > td input").each((index, element) => {
      console.log('item: ', index, element.value);
      if (value === element.value && this !== element) {
        found = true;
      }
    });
    if (found) {
      console.log('item: ', this);
      $(this).css('background-color', 'lightgrey');
      $(this).addClass('indi-form__input--has-error');
    }

    // let clickedCell = $($event.target).closest("td");
    let clickedCell = $($event.target).parent();
    // console.log('cell: ' + JSON.stringify(clickedCell, undefined, 2));
    console.log('cell: ', clickedCell);

    // $("#spnText").html('Clicked table cell value is: <b> ' +
    // clickedCell.val() + value + '</b>');
  });

});

/** Begin: generic validation 3 of 3 */
/*
 * function addRemoveRedBorder($event) { const element = $event.target; if
 * ($(element).valid()) { if
 * (element.classList.contains('indi-form__input--has-error')) {
 * element.classList.remove('indi-form__input--has-error'); } } else { if
 * (!element.classList.contains('indi-form__input--has-error')) {
 * element.classList.add('indi-form__input--has-error'); } } }
 */

/**
 * This is needed only when using 'show/hide' to toggle the form. Most of the
 * time this function is not needed.
 */
/*
 * function clearFormErrors() { $('form')[0].reset(); $('form
 * .indi-form__input--has-error').removeClass('indi-form__input--has-error');
 * $('form label-error').empty(); }
 */
/** Begin: generic validation 3 of 3 */

