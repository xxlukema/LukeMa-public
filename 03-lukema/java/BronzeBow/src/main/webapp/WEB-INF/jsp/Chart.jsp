<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:jsp="http://java.sun.com/JSP/Page">
<head>
<title>Stock Charts</title>
</head>
<body>

<h3>Hello World!</h3>

<jsp:plugin type="applet" code="gui.applet.ConcertLineApplet.class"
	codebase="applets"
	archive="mba-ui-common-by-webservice-1.0.jar,MbaWebServiceLib-1.0.jar,mba-common-1.0.jar"
	width="1040" height="850">
	<jsp:fallback>Unable to start plugin!</jsp:fallback>
</jsp:plugin>

<h3>Hello Again!</h3>
</body>
</html>