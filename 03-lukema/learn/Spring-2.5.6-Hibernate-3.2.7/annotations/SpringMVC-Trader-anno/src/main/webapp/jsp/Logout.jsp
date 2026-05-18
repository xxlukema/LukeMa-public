<%@ include file="/jsp/Include.jspf" %>

<html>
<head>
   <title>Spring MVC</title>
   <link rel="stylesheet" type="text/css" href="css/default.css" />
</head>

<body>

<%@ include file="/jsp/Top.jspf" %>

<% 
   session.removeAttribute("usr"); 
%>

<c:redirect url="/logon.go"/>


</body>
</html>