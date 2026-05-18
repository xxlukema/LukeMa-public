<%@ include file="/jsp/Include.jspf" %>

<body>

<%@ include file="/jsp/Top.jspf" %>

<h1>Portfolio</h1>
<b>Cash:</b> <fmt:formatNumber value="${model.cash}" type="currency" />
<br/>
<br/>
<table border="1">
	<tr>
		<td><b>Symbol</b></td>
		<td><b>Company</b></td>
		<td><b>Price</b></td>
		<td><b>Change</b></td>
		<td><b>% Change</b></td>
		<td><b>Shares</b></td>
		<td><b>Open</b></td>
		<td><b>Volume</b></td>
		<td><b>Current Value</b></td>
		<td><b>Gain/Loss</b></td>
	</tr>
	<c:forEach items="${model.portfolioItems}" var="stock">
	<tr>
		<td><str:upperCase><c:out value="${stock.symbol}"/></str:upperCase></td>
		<td><c:out value="${stock.quote.company}"/></td>
		<td><fmt:formatNumber value="${stock.quote.value}" type="currency" /></td>
		<td>
			<c:choose>
				<c:when test="${stock.quote.change >= 0}">
					<fmt:formatNumber value="${stock.quote.change}" type="currency" />
				</c:when>
				<c:otherwise>
					<font color="red">
						<fmt:formatNumber value="${stock.quote.change}" type="currency" />
					</font>
				</c:otherwise>
			</c:choose>	
		</td>
		<td>
			<c:choose>
				<c:when test="${stock.quote.pctChange >= 0}">
					<fmt:formatNumber value="${stock.quote.pctChange}" type="percent" />
				</c:when>
				<c:otherwise>
					<font color="red">
						<fmt:formatNumber value="${stock.quote.pctChange}" type="percent" />
					</font>
				</c:otherwise>
			</c:choose>	
		</td>
		<td><fmt:formatNumber value="${stock.shares}"/></td>
		<td><fmt:formatNumber value="${stock.quote.openPrice}" type="currency" /></td>
		<td><fmt:formatNumber value="${stock.quote.volume}"/></td>
		<td><fmt:formatNumber value="${stock.currentValue}" type="currency" /></td>
		<td>
			<c:choose>
				<c:when test="${stock.gainLoss >= 0}">
					<fmt:formatNumber value="${stock.gainLoss}" type="currency" />
				</c:when>
				<c:otherwise>
					<font color="red">
						<fmt:formatNumber value="${stock.gainLoss}" type="currency" />
					</font>
				</c:otherwise>
			</c:choose>	
		</td>
	</tr>
	</c:forEach>
</table>
<br>
<a href="<c:url value="trade.go"/>">Make a trade</a><br/>
<a href="<c:url value="/jsp/Logout.jsp"/>">Log out</a>
<br>
</body>
</html>

