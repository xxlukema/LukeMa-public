
echo "Deprecated. This is for Keycloak:16.1.0. Exiting..."

exit -1

password=password

JBOSS_HOME=./

mkdir -p ${JBOSS_HOME}/standalone/configuration

rm -f client.crt  client.keystore  client.truststore
rm -f application.crt  application.keystore  application.truststore
rm -f ${JBOSS_HOME}/standalone/configuration/client.truststore
rm -f ${JBOSS_HOME}/standalone/configuration/application.keystore

keytool -genkeypair -alias localhost -keyalg RSA -keysize 2048 -validity 3650 -keystore application.keystore -dname "cn=localhost,o=Hughes,c=US" -keypass ${password} -storepass ${password}

cp application.keystore ${JBOSS_HOME}/standalone/configuration

keytool -genkeypair -alias client -keyalg RSA -keysize 2048 -validity 365 -keystore client.keystore -dname "CN=client" -keypass ${password} -storepass ${password}

keytool -exportcert  -keystore application.keystore -alias localhost -keypass ${password} -storepass ${password} -file application.crt

keytool -exportcert  -keystore client.keystore -alias client -keypass ${password} -storepass ${password} -file client.crt

keytool -importcert -keystore application.truststore -storepass ${password} -alias client -trustcacerts -file client.crt -noprompt

keytool -importcert -keystore client.truststore -storepass ${password} -alias localhost -trustcacerts -file application.crt -noprompt

cp client.truststore ${JBOSS_HOME}/standalone/configuration

rm -f client.crt  client.keystore  client.truststore
rm -f application.crt  application.keystore  application.truststore

