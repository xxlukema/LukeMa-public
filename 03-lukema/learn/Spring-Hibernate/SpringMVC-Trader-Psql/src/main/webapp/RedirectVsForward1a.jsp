<%@ include file="/WEB-INF/jsp/Include.jsp"%>

<head>
<title>Spring MVC</title>
<link rel="stylesheet" type="text/css" href="css/learn.css" />
</head>

<body>

	<h3>Redirect vs Forward Page 1a</h3>

	<%
	    org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger("RedirectVsForward1a.jsp");

	    LOG.debug("RedirectVsForward1a");

	    LOG.debug("param = " + request.getParameter("param"));

	    String forwardURL = "RedirectVsForward2.jsp";
	    pageContext.forward(forwardURL);
	%>


</body>
</html>

