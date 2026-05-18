$(document).ready(function() {
	$("a").click(function(event) {
		alert("As you can see, the link no longer took you to jquery.com");
		event.preventDefault();
	});

	// $('li').add('p').css('background-color', 'red');

});

(function($) {

	$.fn.tooltip = function(options) {

		// Create some defaults, extending them with any options that were
		// provided
		var settings = $.extend({
			'location' : 'top',
			'background-color' : 'blue'
		}, options);

		return this.each(function() {

			// Tooltip plugin code here

		});

	};
})(jQuery);