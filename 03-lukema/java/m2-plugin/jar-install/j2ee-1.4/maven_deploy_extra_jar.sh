
mvn deploy:deploy-file \
        -Dfile=${SDK_HOME}/lib/j2ee.jar \
        -DgroupId=javax.j2ee \
        -DartifactId=j2ee \
        -Dversion=1.4 \
        -Dpackaging=jar \
        -DrepositoryId=fuelquest \
        -Durl=file:///${TOMCAT_HOME}/webapps/maven2

