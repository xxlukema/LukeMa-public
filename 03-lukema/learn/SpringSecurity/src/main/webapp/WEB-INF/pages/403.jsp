<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Access Denied Page</title>
</head>
<body>
	<c:if test="${not empty error}">
		<div style="color: red">
			Your fake login attempt was bursted, dare again !!<br /> Caused : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		</div>
	</c:if>
	<h1>HTTP Status 403 - Access is denied</h1>
	<h3>Message : ${message}</h3>
</body>
</html>