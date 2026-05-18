<%@ include file="/jsp/Include.jspf" %>

<body>

<%@ include file="/jsp/Top.jspf" %>

<center>

   <h1>Welcome Spring MVC</h1> 

   <h3>usr: guest</h3> 
   <h3>passwd: guest</h3> <br/>

   <form method="post">
      <table width="25%" border="1">
         <tr>
            <td align="center" bgcolor="lightblue">Log on</td>
         </tr>
         <tr>
            <td>
               <table border="0" width="100%">
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
                        <input type="submit" alignment="center" value="Logon">
                     </td>
                  </tr>
               </table>

            </td>
         </tr>
      </table>
   </form>

</center>

</body>
</html>