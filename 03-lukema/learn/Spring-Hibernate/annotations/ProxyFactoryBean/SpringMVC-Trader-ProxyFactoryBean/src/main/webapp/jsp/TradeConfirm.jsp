<%@ include file="/jsp/Include.jspf" %>

<html>
<head>
   <title>Spring MVC</title>
   <link rel="stylesheet" type="text/css" href="css/learn.css" />
</head>

<body>

<%@ include file="/jsp/Top.jspf" %>

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
			<c:out value="${trade.symbol}"/>	
		</td>
		<td>
			<c:out value="${trade.shares}"/>	
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
<a href="<c:url value="/jsp/Logout.jsp"/>">Log out</a>
<br>
</body>
</html>