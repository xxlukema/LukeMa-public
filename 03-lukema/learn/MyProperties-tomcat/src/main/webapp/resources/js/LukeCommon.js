
"use strict";

/**
 * Format digits using grouping
 * 
 * <span class="integer">1234</span>
 * Usage: $('span.integer').digits();
 */
$.fn.digits = function(){ 
    return this.each(function(){ 
		$(this).text($(this).text().replace(/null/g, ""));
    	$(this).text($(this).text().replace(/\.\d*/g, ""));
    	$(this).text($(this).text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"));
    })
}

/**
 * get URL path parameter
 * 
 * Usage: var user = urlParam('user');
 * 
 * returns null if not found.
 */
function urlParam(name) {
	var pageURL = window.location.search.substring(1);
    var urlVariables = pageURL.split('&');
    for (var i = 0; i < urlVariables.length; i++) {
        var parameterName = urlVariables[i].split('=');
        if (parameterName[0] == name) {
            return parameterName[1];
        }
    }
    
    return null;
}

