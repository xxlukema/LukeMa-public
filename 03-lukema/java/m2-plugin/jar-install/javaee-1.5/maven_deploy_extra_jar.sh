
mvn deploy:deploy-file \
        -Dfile=javaee.jar \
        -DgroupId=javaee \
        -DartifactId=javaee \
        -Dversion=1.5 \
        -Dpackaging=jar \
        -DrepositoryId=javaee \
        -Durl=file:///${TOMCAT_HOME}/webapps/maven2


