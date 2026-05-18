openssl req -new -text -passout pass:luke -subj /CN=localhost -out server.req -keyout privkey.pem
openssl rsa -in privkey.pem -passin pass:luke -out server.key
openssl req -x509 -in server.req -text -key server.key -out server.crt -days 3650
chmod 600 server.key
# test $(uname -s) = Linux && chown 70 server.key
# docker run -d --name postgres -e POSTGRES_HOST_AUTH_METHOD=trust -v "$(pwd)/server.crt:/var/lib/postgresql/server.crt:ro" -v "$(pwd)/server.key:/var/lib/postgresql/server.key:ro" postgres:12-alpine -c ssl=on -c ssl_cert_file=/var/lib/postgresql/server.crt -c ssl_key_file=/var/lib/postgresql/server.key

# openssl x509 -in server.crt -text -noout

