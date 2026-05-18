<html>

<%@ page import="javax.naming.*, javax.jms.*, com.learn.session.BookSessionBeanLocal" %> 

<%@ page session="false" %>


<%@page import="javax.servlet.http.HttpSession"%>

<head>
<title>Learn JSP 2.0</title>

<script type="text/javascript">
/*
   function window.close()
   {
      alert("Window closing...");
   }
   */
</script>

<script src="js/name.js">
</script>


</head>
<body BGCOLOR="WHEAT" ONLOAD="windowInfo()">
<h2>Hello World!</h2>

<div ID="names">Names here</div>

  

   <%
         out.println("Calling request.getSession(false)...");
         HttpSession s = request.getSession(false);

         if (s == null)
         {
            out.println("Session is null.");
            out.println("<br/>");

            s = request.getSession(true);
            out.println("New session id: " + s.getId());
            out.println("<br/>");
            boolean isNew = s.isNew();
            out.println("Session isNew: " + isNew);
            out.println("<br/>");
         }
         else
         {
            out.println("Session already existed.");
            out.println("<br/>");

            String sid = s.getId();
            out.println("Session Id: " + sid);
            out.println("<br/>");
            boolean isNew = s.isNew();
            out.println("Session isNew: " + isNew);
            out.println("<br/>");
         }

         // BookSessionBeanLocal beanLocal = (BookSessionBeanLocal) context.lookup("EJB3JBoss-packaging/BookSessionBean/local");
         // beanLocal.test();
                           
         Context context = new InitialContext();

         Queue queue = (Queue) context.lookup("queue/myQueue");
         QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
         QueueConnection cnn = factory.createQueueConnection();
         QueueSession session = cnn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

         TextMessage msg = session.createTextMessage("Hello World");

         QueueSender sender = session.createSender(queue);
         sender.send(msg);
         
         out.println("Message sent successfully to remote queue.");
   %>

   <br/>

   <h3>Open new windows:</h3>

  
</body>
</html>
