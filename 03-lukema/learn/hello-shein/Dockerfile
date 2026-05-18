FROM nginx:1.26.0-alpine-slim

# Enable TLS
RUN mkdir -p /etc/nginx/ssl/
COPY ./tls/server.* /etc/nginx/ssl/
COPY ./docker-conf/default.conf /etc/nginx/conf.d/
COPY ./dist/hello-shein/ /usr/share/nginx/html/

# ZeroSSL `HTTP File Upload` Verification
RUN mkdir -p /usr/share/nginx/html/.well-known/pki-validation
COPY ./zerossl/ /usr/share/nginx/html/.well-known/pki-validation/

EXPOSE 443
EXPOSE 80
