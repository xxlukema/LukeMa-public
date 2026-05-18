<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta content="no-cache" http-equiv="Cache-Control" />
<meta content="no-cache" http-equiv="Pragma" />
<meta http-equiv="EXPIRES" content="0" />

<title>Spring MVC</title>
<link rel="stylesheet" type="text/css" href="css/learn.css" />

<style type="text/css">
div {
	margin-bottom: 20px;
	padding: 2px;
	display: block;
}
</style>

<script src="https://code.jquery.com/jquery-3.2.1.slim.js"></script>

</head>

<body>

	<div style="align: center;">

		<h1>Welcome Spring MVC</h1>

		<br />

		<form:form>
			<table class="attribute">
				<tr>
					<th colspan="2">Hello Output says "${greetings}".</th>
				</tr>
				<tr>
					<td width="33%" align="right"><form:label path="firstName">First Name:</form:label></td>
					<td width="66%" align="left"><form:input disabled="true" path="firstName" /></td>
				</tr>
				<tr>
					<td width="33%" align="right"><form:label path="lastName">Last Name:</form:label></td>
					<td width="66%" align="left"><form:input disabled="true" path="lastName" /></td>
				</tr>
				<tr>
					<td width="33%" align="right"><form:label path="age">Age:</form:label></td>
					<td width="66%" align="left"><form:input disabled="true" path="age" /></td>
				</tr>
			</table>
		</form:form>

	</div>

</body>
</html>
