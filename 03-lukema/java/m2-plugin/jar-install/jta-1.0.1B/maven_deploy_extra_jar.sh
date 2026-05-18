
mvn deploy:deploy-file \
        -Dfile=jta-1_0_1B-classes.zip \
        -DgroupId=javax.transaction \
        -DartifactId=jta \
        -Dversion=1.0.1B \
        -Dpackaging=jar \
        -DrepositoryId=fuelquest \
        -Durl=file:///${TOMCAT_HOME}/webapps/maven2

