

curl -k -i -X GET "https://localhost:8443/spring/user/ping"

mvn spring-boot:run



curl -k -i -H "Content-Type: application/x-www-form-urlencoded" -X POST -F 'id=101' -F "summary=sum" -F "description=My Desc" http://localhost:8080/HelloHoustonService/rest/todoCrud/formPost

curl -k -i -H "Accept: application/json" -H "Content-Type: application/json" -d '{"id":"102", "summary":"sum", "description":"My desc"}' -X POST  http://localhost:8080/HelloHoustonService/rest/todoCrud/jsonPost
curl -k -i -H "Accept: application/json" -H "Content-Type: application/json" -d @todo.json -X POST  http://localhost:8080/HelloHoustonService/rest/todoCrud/jsonPost

curl -k -i -H "Accept: application/json" -H "Content-Type: application/xml" -d "<todo><id>101</id><summary>sum</summary><description>My Desc</description></todo>" -X POST  http://localhost:8080/HelloHoustonService/rest/todoCrud/xmlPost
curl -k -i -H "Accept: application/json" -H "Content-Type: application/xml" -d @todo.xml -X POST http://localhost:8080/HelloHoustonService/rest/todoCrud/xmlPost


curl -k -i -H "Accept: application/json" -H "Content-Type: application/xml" -H "MyHeaderParam: Luke\r\nHeader%0D%0A%0d%0aParam\r\n%0D%0A%0d%0aLine Two: Another%0D%0Line" -d @todo.xml -X POST http://localhost:8080/HelloHoustonService/rest/todoCrud/xmlPost


curl -i -H "Content-Type: application/json" -X GET http://localhost:8080/HelloHoustonService/rest/todoCrud/callJSon/101

curl -v -k -H "Accept: application/json" -H "Content-Type: application/json" -d '{"id":"59b0dcfb5753540bdc949a42", "summary":"sum", "details":"My details"}' "http://localhost:8080/HelloHoustonService/rest/rent/addRentProperty"



