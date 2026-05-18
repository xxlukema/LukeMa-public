
mvn deploy:deploy-file \
        -Dfile=collection-setter-injector.jar \
        -DgroupId=org.jvnet.jaxb2-commons \
        -DartifactId=collection-setter-injector \
        -Dversion=1.0 \
        -Dpackaging=jar \
        -DrepositoryId=local \
        -Durl=file:///${TOMCAT_HOME}/webapps/maven2

