
# `hello-boot-jwt-csrf-tokens`

## `JWT` Security

    # 1. Generate JWS Cookie:
    curl -c cookies.txt -X POST 'http://localhost:8080/jwtlogin'  \
         -H "Content-Type: application/json" -d '{"username": "admin", "password": "admin"}'
    # curl -u "admin:admin" -c cookies.txt -X POST 'http://localhost:8080/jwtlogin'
       
    # 2. Use that cookie to access secured sites:
    curl -i -k -b cookies.txt -X GET 'http://localhost:8080/jwt/ping'
    
    # 3. Not using that cookie to access secured sites will fail (no cookie):
    curl -i -k -X GET 'http://localhost:8080/jwt/ping'
    

## `CSRF` Token


    # 1. There is no CSRF token for READ Actions:
    curl -i -k -L -X GET 'http://localhost:8080/csrf/get'
    
    # 2. CSRF tokens are for Write/Update Actions:
    curl -i -k -X POST 'http://localhost:8080/csrf/post' -H "X-CSRF-TOKEN: OK" -u "admin:admin"
    # Or
    curl -i -k -X POST 'http://localhost:8080/csrf/post?_csrf=OK' -u "admin:admin"
    
    # 3. This will fail (bad token):
    curl -i -k -X POST 'http://localhost:8080/csrf/post' -H "X-CSRF-TOKEN: OK2" -u "admin:admin"

    # 4. This will fail (unauthorized):
    curl -i -k -X POST 'http://localhost:8080/csrf/post' -H "X-CSRF-TOKEN: OK"
    
    # 5. CSRF bypass:
    curl -i -k -X POST 'http://localhost:8080/csrf/bypass' -u "admin:admin"
    
    # 6. CSRF bypass fail (unauthorized):
    curl -i -k -X POST 'http://localhost:8080/csrf/bypass'
    
## Outside of `CSRF` and `JWT`

    # 1. There is no CSRF token for READ Actions:
    curl -i -k -L -X GET 'http://localhost:8080/ping/get'
    
    # 2. There is no CSRF token for READ Actions:
    curl -i -k -X POST 'http://localhost:8080/ping/post' -u "admin:admin"
    
    # 3. This will fail (unauthorized):
    curl -i -k -X POST 'http://localhost:8080/ping/post'



