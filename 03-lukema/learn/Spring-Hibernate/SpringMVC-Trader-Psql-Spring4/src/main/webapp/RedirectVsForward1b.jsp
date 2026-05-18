<%@ include file="/WEB-INF/jsp/Include.jsp"%>

<head>
<title>Spring MVC</title>
<link rel="stylesheet" type="text/css" href="css/learn.css" />
</head>

<body>

	<h3>Redirect vs Forward Page 1b</h3>

	<%
	    org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger("RedirectVsForward1b.jsp");

	    LOG.debug("RedirectVsForward1b");

	    LOG.debug("param = " + request.getParameter("param"));

	    String redirectURL = "RedirectVsForward2.jsp";
	    response.sendRedirect(redirectURL);
	%>


</body>
</html>

