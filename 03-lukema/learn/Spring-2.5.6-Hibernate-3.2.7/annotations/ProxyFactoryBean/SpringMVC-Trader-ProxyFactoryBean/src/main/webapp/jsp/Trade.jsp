<%@ include file="/jsp/Include.jspf" %>

<html>
<head>
   <title>Spring MVC</title>
   <link rel="stylesheet" type="text/css" href="css/learn.css" />
</head>

<body>

<%@ include file="/jsp/Top.jspf" %>

<h1>Trade</h1>
<form method="post">

<!-- first bind on the object itself to display global errors - if available -->
<spring:bind path="trade.*">
    <font color="red">
        <c:forEach items="${status.errorMessages}" var="error">
            Error: <c:out value="${error}"/><br/>
        </c:forEach>
    </font>
    <br/>
</spring:bind>

<table class="attribute">
    <tr>
        <th>Buy/Sell</th>
        <th>Symbol</th>
        <th>Shares</th>
    </tr>
    <tr>
        <td>
            <spring:bind path="trade.buySell">
            <input type="radio" 
                   name="buySell" 
                   value="true" 
                   <c:if test="${status.value}">checked</c:if> >
                Buy
            <input type="radio" 
                   name="buySell" 
                   value="false" 
                   <c:if test="${! status.value}">checked</c:if> >
                Sell
            </spring:bind>            
        </td>
        <td>
            <spring:bind path="trade.symbol">
                <input type="text" name="symbol" value="<c:out value="${status.value}"/>"/>
            </spring:bind>
        </td>
        <td>
            <spring:bind path="trade.shares">
                <input type="text" name="shares" value="<c:out value="${status.value}"/>"/>
            </spring:bind>
        </td>
    </tr>
    <tr>
        <td colspan="3" align="center"><input type="submit" name="_target1" value="Execute Order"></td>
    </tr>
</table>

    
</form>
<br>
<a href="<c:url value="portfolio.go"/>">View Portfolio</a><br/>
<a href="<c:url value="/jsp/Logout.jsp"/>">Log out</a>
<br>
</body>
</html>