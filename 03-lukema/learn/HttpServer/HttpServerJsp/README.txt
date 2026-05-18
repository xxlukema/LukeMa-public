
This HttpServer is to print out what web browser sent to the server. 

0. URL:

      http://localhost:8090/HttpServerJsp/

1. Start this server:

   1.1 mvn clean package -Dmaven.test.skip=true

   1.2 run web server (tomcat)

2. Access the server using: 

      http://localhost:8090/HttpServerJsp/?parm1=a&parm2=bb

   or sumbit data from 

      src/main/webapp/client/FormPostHttp.htm   --- plain text request
      src/main/webapp/client/FormPostHttps.htm  --- Encrypted request

