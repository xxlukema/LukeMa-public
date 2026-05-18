# https://www.keycloak.org/server/enabletls

mkdir -p tls

# https://stackoverflow.com/questions/10175812/how-to-generate-a-self-signed-ssl-certificate-using-openssl

openssl req -new -x509 -nodes -days 3650 -newkey rsa:2048 -sha256 \
  -out ./tls/server.crt -keyout ./tls/server.key -subj "/CN=localhost" \
  -subj "/C=US/ST=Maryland/L=Germantown/O=Hughes, Inc./OU=NMS/CN=localhost"

# -nodes: no DES - No pass phrase
# -subj "/C=US/ST=Oregon/L=Portland/O=Company Name/OU=Org/CN=www.example.com"
# openssl req -new -x509 -nodes -days 3650 -newkey rsa:2048 -out ./tls/server.crt -keyout ./tls/server.key

# print self-signed cert:
# openssl x509 -in ./tls/server.crt -text -noout
