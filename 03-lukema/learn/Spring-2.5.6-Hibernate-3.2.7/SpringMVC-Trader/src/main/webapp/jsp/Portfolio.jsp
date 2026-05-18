<%@ include file="/jsp/Include.jsp" %>

<html>

<%@ include file="/jsp/Head.jsp" %>

<body>

<%@ include file="/jsp/Top.jsp" %>

<h1>Portfolio</h1>
<b>Cash:</b> <fmt:formatNumber value="${model.cash}" type="currency" />
<br/>
<br/>
<table class="attribute">
	<tr>
		<th>Symbol</th>
		<th>Company</th>
		<th>Price</th>
		<th>Change</th>
		<th>% Change</th>
		<th>Shares</th>
		<th>Open</th>
		<th>Volume</th>
		<th>Current Value</th>
		<th>Gain/Loss</th>
	</tr>
	<c:forEach items="${model.portfolioItems}" var="stock">
	<tr>
		<td><str:upperCase>${stock.symbol}</str:upperCase></td>
		<td>${stock.quote.company}</td>
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

