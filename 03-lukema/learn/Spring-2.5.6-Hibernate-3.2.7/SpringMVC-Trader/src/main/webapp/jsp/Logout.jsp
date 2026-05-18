<%@ include file="/jsp/Include.jsp" %>

<html>

<%@ include file="/jsp/Head.jsp" %>

<body>

<%@ include file="/jsp/Top.jsp" %>

<% 
   session.removeAttribute("usr"); 
%>

<c:redirect url="/logon.go"/>


</body>
</html>