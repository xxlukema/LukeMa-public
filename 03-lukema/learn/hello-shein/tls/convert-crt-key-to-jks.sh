
openssl pkcs12 -export -name boot -in server.crt -inkey server.key -out myp12keystore.p12 -password pass:changeme

keytool -importkeystore -destkeystore server.jks -srckeystore myp12keystore.p12 -srcstoretype pkcs12 -alias boot -srcstorepass changeme -storepass changeme
rm myp12keystore.p12

keytool -list -v -keystore server.jks -storepass changeme

