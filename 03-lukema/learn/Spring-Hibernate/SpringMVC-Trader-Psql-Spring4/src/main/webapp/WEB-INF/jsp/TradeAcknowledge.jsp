<%@ include file="/WEB-INF/jsp/Include.jsp" %>

<%@ include file="/WEB-INF/jsp/Head.jsp" %>

<body>

<%@ include file="/WEB-INF/jsp/Top.jsp" %>

<h1>Successful Trade Acknowledgement</h1>
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
				<c:when test="${trade.buySell == true}">Bought</c:when>
				<c:otherwise>Sold</c:otherwise>
			</c:choose>
		</td>
		<td>
			${trade.symbol}	
		</td>
		<td>
			${trade.shares}	
		</td>
	</tr>
</table>

<h2>Your order was filled</h2>
    
</form>
<br>
<a href="<c:url value="Portfolio.go"/>">View Portfolio</a><br/>
<a href="<c:url value="Logout.go"/>">Log out</a>
<br>
</body>
</html>