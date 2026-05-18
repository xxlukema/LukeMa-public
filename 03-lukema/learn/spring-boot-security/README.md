# spring security

## OK
```
curl -k -i --user user:user -X GET "http://localhost:8080/spring/ping"
curl -k -i --user admin:admin -X GET "http://localhost:8080/spring/employees"
```

## DENY: USER has no access to /employees path
```
curl -k -i --user user:user -X GET "http://localhost:8080/spring/employees"
```
