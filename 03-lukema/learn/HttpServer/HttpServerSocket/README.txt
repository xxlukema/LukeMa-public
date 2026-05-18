
This HttpServer is to print out what web browser sent to the server. 

0. URL:

      http://localhost:9090/

1. Start this server:

      mvn clean test

2. Access the server using: 

      http://localhost:9090/?parm1=a&parm2=bb

   or sumbit data from 

      src/main/webapp/client/FormPostHttp.htm    --- plain text request

3. Don't Knows:
 
     3.1 How to read POST parameters.

     3.2 How to process encrypted data:
     
        src/main/webapp/client/FormPostHttps.htm   --- encrypted text request


