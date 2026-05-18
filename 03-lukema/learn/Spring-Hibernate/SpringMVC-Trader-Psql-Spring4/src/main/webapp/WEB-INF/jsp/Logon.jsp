<%@ include file="/WEB-INF/jsp/Include.jsp"%>

<%@ include file="/WEB-INF/jsp/Head.jsp"%>

<body>

   <%@ include file="/WEB-INF/jsp/Top.jsp"%>

   <div style="align: center;">

      <h1>Welcome Spring MVC</h1>

      <h3>Username: guest</h3>
      <h3>Password: guest</h3>
      <br />

      <h4>Message Boundle:</h4>

      <form:form method="post" action="Logon.go" modelAttribute="credentials">
         <table class="attribute">
            <tr>
               <th colspan="2">Log On</th>
            </tr>
            <tr>
               <td width="33%" align="right"><form:label path="username">Username:</form:label></td>
               <td width="66%" align="left"><form:input path="username" /></td>
            </tr>
            <tr>
               <td width="33%" align="right"><form:label path="password">Password:</form:label></td>
               <td width="66%" align="left"><form:input path="password" /></td>
            </tr>
            <tr>
               <td align="center" colspan="2"><input type="submit" value="Logon" /></td>
            </tr>
         </table>
      </form:form>

   </div>

</body>
</html>
