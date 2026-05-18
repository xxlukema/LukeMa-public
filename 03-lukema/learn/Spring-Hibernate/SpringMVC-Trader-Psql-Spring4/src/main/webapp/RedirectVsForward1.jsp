<%@ include file="/WEB-INF/jsp/Include.jsp"%>

<head>
<title>Spring MVC</title>
<link rel="stylesheet" type="text/css" href="css/learn.css" />
</head>

<body>

	<h3>Redirect vs Forward Page 1</h3>

	<div>
		<b>Redirect prevents "Refresh/Re-Submit". After a row in database
			is inserted/updated/deleted, it should REDIRECT to result page for
			database read to prevent double update to database.</b>
	</div>

	<div>
		<b>After forward, browser URL does not change. Refresh browser
			will cause RedirectVsForward1a.jsp and RedirectVsForward2.jsp be
			executed.</b>
	</div>
	<div>
		<input type="button" value="Click to Forward"
			onClick="javascript:window.location='RedirectVsForward1a.jsp?param=My%20Parameter';" />
	</div>
	<div>
		<b>After redirect, browser URL will change. Refresh browser will
			cause only RedirectVsForward2.jsp be executed.</b>
	</div>
	<div>
		<input type="button" value="Click to Redirect"
			onClick="javascript:window.location='RedirectVsForward1b.jsp?param=My%20Parameter';" />
	</div>


</body>
</html>

