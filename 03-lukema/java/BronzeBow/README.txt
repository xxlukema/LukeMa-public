


${TOMCAT_HOME}\conf\server.xml


<Service name="Catalina">

   <Connector port="8080" protocol="org.apache.coyote.http11.Http11NioProtocol"
               connectionTimeout="20000"
               redirectPort="8443" />
               
   <Connector port="8009" protocol="org.apache.coyote.http11.Http11NioProtocol" redirectPort="8443" />
   
   
</Service>
