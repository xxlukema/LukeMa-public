<%@ include file="/WEB-INF/jsp/Include.jsp"%>

<%@ include file="/WEB-INF/jsp/Head.jsp" %>

<body>

	<%@ include file="/WEB-INF/jsp/Top.jsp"%>

	<h1>Trade Not Allowed</h1>
	<form method="post">

		<table class="attribute">
			<tr>
				<th>Buy/Sell</th>
				<th>Exception</th>
			</tr>
			<tr>
				<td><c:choose>
						<c:when test="${trade.buySell == true}">Buy</c:when>
						<c:otherwise>Sell</c:otherwise>
					</c:choose></td>
				<td>${trade.exception}</td>
			</tr>
		</table>

		<h2>Your order was filled</h2>

	</form>
	<br>
	<a href="<c:url value="portfolio.go"/>">View Portfolio</a>
	<br />
	<a href="<c:url value="/jsp/Logout.jsp"/>">Log out</a>
	<br>
</body>
</html>