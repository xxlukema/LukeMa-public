function submitEnter(commandId, e) {
	let keycode;
	if (e) {
		keycode = e.keyCode || e.which;
	} else {
		return true;
	}

	if (keycode == 13) {
		document.getElementById(commandId).click();
		return false;
	} else {
		return true;
	}
}

function isEmpty(val){
	return (typeof val == "undefined" || val === undefined || val == null || val == "");
}

$.fn.digits = function(){
    return this.each(function(){
    	$(this).text($(this).text().replace(/null/g, ""));
    	$(this).text($(this).text().replace(/\.\d*/g, ""));
    	$(this).text($(this).text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"));
    })
}


