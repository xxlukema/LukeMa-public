<%@ include file="/jsp/Include.jspf" %>

<html>
<head>
   <title>Spring MVC</title>
   <link rel="stylesheet" type="text/css" href="css/learn.css" />
</head>

<body>

<%@ include file="/jsp/Top.jspf" %>

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
			<c:out value="${trade.symbol}"/>	
		</td>
		<td>
			<c:out value="${trade.shares}"/>	
		</td>
	</tr>
</table>

<h2>Your order was filled</h2>
    
</form>
<br>
<a href="<c:url value="portfolio.go"/>">View Portfolio</a><br/>
<a href="<c:url value="/jsp/Logout.jsp"/>">Log out</a>
<br>
</body>
</html>