
function stopEvent() {
	if(event == null) {
		var event = window.event;
	}
	
	//e.cancelBubble is supported by IE - this will kill the bubbling process.
	event.cancelBubble = true;
	event.returnValue = false;

	//event.stopPropagation works only in Firefox.
	if (event.stopPropagation) {
		event.stopPropagation();
		event.preventDefault();
	}
	
	return false;
}

