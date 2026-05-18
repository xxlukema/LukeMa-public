<%@ include file="/jsp/Include.jsp" %>

<html>

<%@ include file="/jsp/Head.jsp" %>

<body>

<%@ include file="/jsp/Top.jsp" %>

<div style="align: center;">

   <h1>Welcome Spring MVC</h1> 

   <h3>Username: guest</h3> 
   <h3>Password: guest</h3> <br/>
   
   <h4>Message Boundle: <spring:message code="error.login.not-specified" /></h4>

   <form method="post">
      <table class="attribute">
         <tr>
            <th colspan="2">Log On</th>
         </tr>
         <tr>
            <td width="33%" align="right">Username: </td>
            <td width="66%" align="left">
               <spring:bind path="credentials.username">
                  <input type="text" name="username" value="${status.value}"/>
               </spring:bind>
            </td>
         </tr>
         <tr>
            <td colspan="2" align="center">
               <spring:hasBindErrors name="credentials">
                  <font color="red">${status.errorMessage}</font>
               </spring:hasBindErrors>
            </td>
         </tr>
         <tr>
            <td width="33%" align="right">Password: </td>
            <td width="66%" align="left">
               <spring:bind path="credentials.password">
                  <input type="password" name="password" value="${status.value}"/>
               </spring:bind>
            </td>
         </tr>
         <tr>
            <td colspan="2" align="center">
               <spring:hasBindErrors name="credentials">
                  <font color="red">${status.errorMessage}</font>
               </spring:hasBindErrors>
            </td>
         </tr>
         <tr>
            <td align="center" colspan="2">
               <input type="submit" value="Logon">
            </td>
         </tr>
      </table>
   </form>

</div>

</body>
</html>
