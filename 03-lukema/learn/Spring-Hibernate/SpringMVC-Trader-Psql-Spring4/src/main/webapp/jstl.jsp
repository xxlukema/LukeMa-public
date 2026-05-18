<%@ include file="/WEB-INF/jsp/Include.jsp" %>

<head>
<title>Tag Example</title>
</head>

<body>
	<c:set var="salary" scope="session" value="${2000*2}" />
	<c:if test="${salary > 2000}">
		<p>My salary is: ${salary}
		<p>
	</c:if>
</body>
</html>