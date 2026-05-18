<%@ include file="/jsp/Include.jspf" %>

<body>

<%@ include file="/jsp/Top.jspf" %>

<% 
   session.removeAttribute("usr"); 
%>

<c:redirect url="/logon.go"/>


</body>
</html>