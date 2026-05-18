<%@ include file="/WEB-INF/jsp/Include.jsp"%>

<head>
<title>Spring MVC</title>
<link rel="stylesheet" type="text/css" href="css/learn.css" />
</head>

<body>

	<h3>Redirect vs Forward Page 2</h3>

	<div>
		<b>If you are forwarded here, browser URL does not change. Refresh
			browser will cause RedirectVsForward1a.jsp and RedirectVsForward2.jsp
			be executed.</b>
	</div>
	<div>
		<b>If you are redirected here, browser URL will change. Refresh
			browser will cause only RedirectVsForward2.jsp be executed.</b>
	</div>
	<div>
		<b>Redirect prevents "Refresh/Re-Submit". After a row in database
			is inserted/updated/deleted, it should REDIRECT to result page for
			database read to prevent double update to database.</b>
	</div>

	<%
	    org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger("RedirectVsForward2.jsp");

	    LOG.debug("RedirectVsForward2");

	    LOG.debug("param = " + request.getParameter("param"));
	%>

</body>
</html>

