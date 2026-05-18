<html>
<head>

   <title>Test Cookie</title>

   <script type="text/javascript" SRC="../js/CookieLib.js">
   </script> 

   <script type="text/javascript" SRC="../js/MyCookie.js">
   </script> 

</head>

<body ONLOAD="">

<h3>Test Cookie</h3>

<%
   String host = request.getRemoteHost();
   request.setAttribute("host", host);
%>

<input type="BUTTON" VALUE="Read Cookies" ONCLICK="readCookies();" />

<input type="BUTTON" VALUE="Create Cookies" ONCLICK="createCookies('${host}');" />

<input type="BUTTON" VALUE="Delete Cookies" ONCLICK="deleteCookies();" />


</body>
</html>

