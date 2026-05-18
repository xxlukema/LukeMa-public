<%@ include file="/WEB-INF/jsp/Include.jsp" %>

<%@ include file="/WEB-INF/jsp/Head.jsp" %>

<body>

<%@ include file="/WEB-INF/jsp/Top.jsp" %>

<h1>Trade Confirmation</h1>
<form method="post">

<table class="attribute">
	<tr>
		<th>Buy/Sell</th>
		<th>Symbol</th>
		<th>Shares</th>
	</tr>
	<tr>
		<td>
			<c:choose>
				<c:when test="${trade.buySell == true}">Buy</c:when>
				<c:otherwise>Sell</c:otherwise>
			</c:choose>
		</td>
		<td>
			${trade.symbol}
		</td>
		<td>
			${trade.shares}	
		</td>
	</tr>
	<tr>
		<td colspan="3" align="center">
			<input type="submit" name="_finish" value="Execute Order">
			<input type="submit" name="_cancel" value="Cancel Order">
		</td>
	</tr>
</table>

    
</form>
<br>
<a href="<c:url value="Logout.go" />">Log out</a>
<br>
</body>
</html>