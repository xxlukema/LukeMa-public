<%@ page import="java.util.Enumeration" %>
<%@ page import="javax.servlet.http.Cookie" %>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<html>

<title id="title">HttpServerJsp</title>

<body BGCOLOR="WHEAT">
<h2>Hello World!</h2>

<h4>This is an http request echo server. It displays request data into the web client.</h4>

<%

   out.println("<br/>");
   out.println("<h3>Web Client Info: </h3>");

   String user = request.getRemoteUser();
   out.println("User: " + user);
   out.println("<br/>");

   String host = request.getRemoteHost();
   out.println("Host: " + host);
   out.println("<br/>");

   String ip = request.getRemoteAddr();
   out.println("Ip: " + ip);
   out.println("<br/>");

   String pathInfo = request.getPathInfo();
   out.println("pathInfo: " + pathInfo);
   out.println("<br/>");

   out.println("<br/>");
   out.println("<h3>Headers: </h3>");

   for (Enumeration e=request.getHeaderNames(); e.hasMoreElements(); )
   {
      String header = (String) (e.nextElement());
      String value = request.getHeader(header);

      out.println(header+": "+value);
      out.println("<br/>");
   }

   out.println("<br/>");
   out.println("<h3>URL Line Request Parameters: </h3>");
   out.println("<p>Note: Request parameters are sent from URL online parameters or by submiting FORM POST data. The server gets the parameters from the HttpServletRequest.");
   out.println("<h5>(You must post to this URL in order to get the POSTed parameter values for this URL.)</h5>");

   for (Enumeration e=request.getParameterNames(); e.hasMoreElements(); )
   {
      String param = (String) (e.nextElement());
      String value = request.getParameter(param);

      out.println(param+": "+value);
      out.println("<br/>");
   }

   out.println("<br/>");
   out.println("<h3>Request Cookies: </h3>");
   out.println("<h5>(Your cookies must be set to this PATH in order to see the cookies for this path.)</h5>");

   Cookie [] cookies = request.getCookies();

   if (cookies == null || cookies.length == 0)
   {
      out.println("No cookies.");
      out.println("<br/>");
   }
   else
   {
      for (int i=0; i<cookies.length; i++)
      {
         String name = cookies[i].getName();
         out.println("Name: " + name);

         String value = cookies[i].getValue();
         out.println("Value: " + value);

         int maxAge = cookies[i].getMaxAge();
         out.println("MaxAge: " + maxAge);
         out.println("; ");

         String path = cookies[i].getPath();
         out.println("Path: " + path);
         out.println("; ");

         String domain = cookies[i].getDomain();
         out.println("Domain: " + domain);

         out.println("<br/>");
      }
   }

   out.println("<br/>");
   out.println("<h3>Request Attributes: </h3>");
   out.println("<p>Note: Request attributes are set and got at the server side.");

   for (Enumeration e=request.getAttributeNames(); e.hasMoreElements(); )
   {
      String attrib = (String) (e.nextElement());
      Object obj = request.getAttribute(attrib);

      if (obj != null)
      {
         if (obj instanceof String)
         {
            String value = (String) obj;
            out.println(attrib+" value: "+value);
            out.println("<br/>");
         }
         else
         {
            String type = obj.getClass().getName();
            out.println(attrib+" type: "+type);
            out.println("<br/>");
         }
      }
      else
      {
         out.println(attrib+" is null.");
         out.println("<br/>");
      }
   }

   out.println("<br/>");
   out.println("<br/>");
   out.println("<br/>");
   out.println("<br/>");
   out.println("<br/>");

%>

   
</body>
</html>
