

curl -k -i -H "Content-Type: application/x-www-form-urlencoded" -X POST -F 'id=101' -F "summary=sum" -F "description=My Desc" http://localhost:8080/MyRest/rest/todoCrud/formPost

curl -k -i -H "Accept: application/json" -H "Content-Type: application/json" -d '{"id":"102", "summary":"sum", "description":"My desc"}' -X POST  http://localhost:8080/MyRest/rest/todoCrud/jsonPost
curl -k -i -H "Accept: application/json" -H "Content-Type: application/json" -d @todo.json -X POST  http://localhost:8080/MyRest/rest/todoCrud/jsonPost

curl -k -i -H "Accept: application/json" -H "Content-Type: application/xml" -d "<todo><id>101</id><summary>sum</summary><description>My Desc</description></todo>" -X POST  http://localhost:8080/MyRest/rest/todoCrud/xmlPost
curl -k -i -H "Accept: application/json" -H "Content-Type: application/xml" -d @todo.xml -X POST http://localhost:8080/MyRest/rest/todoCrud/xmlPost


curl -k -i -H "Accept: application/json" -H "Content-Type: application/xml" -H "MyHeaderParam: Luke\r\nHeader%0D%0A%0d%0aParam\r\n%0D%0A%0d%0aLine Two: Another%0D%0Line" -d @todo.xml -X POST http://localhost:8080/MyRest/rest/todoCrud/xmlPost


curl -i -H "Content-Type: application/json" -X GET http://localhost:8080/MyRest/rest/todoCrud/callJSon/101



Proxy Settings:

    /**
     * http://www.it.iitb.ac.in/~roshan/articles/javahttpproxy.html
     * 
     * JVM:
     * java -Dhttp.proxyHost=proxyhostURL 
     * -Dhttp.proxyPort=proxyPortNumber 
     * -Dhttp.proxyUser=someUserName 
     * -Dhttp.proxyPassword=somePassword javaClassToRun
     * 
     * Catalina:
     * ${CATALINA_OME}/conf/catalina.properties
     * http.proxyHost=yourProxyURL
     * http.proxyPort=yourProxyPort
     * http.proxyUser=yourUserName
     * http.proxyPassword=yourPassword
     * 
     * ${CATALINA_HOME}/bin/catalina.bat
     * JAVA_OPTS="-Dhttp.proxyHost=yourProxyURL ..." 
     * 
     */

@org.testng.annotations.BeforeTest
@org.testng.annotations.Test(enabled = false, dependsOnMethods = { "sleep" }, invocationCount = 100, invocationTimeOut = 100, timeOut = 2000000, threadPoolSize = 1, singleThreaded = true)






     