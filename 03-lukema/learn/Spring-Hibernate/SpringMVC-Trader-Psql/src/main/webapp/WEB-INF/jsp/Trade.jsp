<%@ include file="/WEB-INF/jsp/Include.jsp"%>

<%@ include file="/WEB-INF/jsp/Head.jsp"%>

<body>

	<%@ include file="/WEB-INF/jsp/Top.jsp"%>

	<h1>Trade</h1>

	<table class="attribute">
		<tr>
			<th>Buy/Sell</th>
			<th>Symbol</th>
			<th>Shares</th>
			<th>Price</th>
		</tr>
		<form:form id="inputForm" method="post" action="Trade.go"
			modelAttribute="trade">

			<tr>
				<td><input type="radio" name="buySell" value="false" /> Buy <input
					type="radio" name="buySell" value="false" /> Sell</td>
				<td><input type="text" name="symbol" value="${status.value}"></input></td>
				<td><input type="text" name="shares" value="${status.value}"></input></td>
				<td><input type="text" name="price" value="${status.value}"></input></td>
			</tr>
			<tr>
				<td colspan="4" align="center"><input id="submitButtonDisable"
					type="submit"
					onClick="this.form.submit(); this.disabled=true; this.value='Sending...'; "
					name="_target1" value="Execute Order (Disable Button)" /></td>
			</tr>

		</form:form>

		<tr>
			<td colspan="4" align="center"><input id="submitButtonBlockUI"
				type="submit" onClick="$('#inputForm').submit();" name="_target1"
				value="Execute Order (BlockUI)" /></td>
		</tr>

	</table>


	<br />
	<a href="<c:url value="Portfolio.go"/>">View Portfolio</a>
	<br />
	<a href="<c:url value="Logout.go"/>">Log out</a>
	<br />
</body>
</html>