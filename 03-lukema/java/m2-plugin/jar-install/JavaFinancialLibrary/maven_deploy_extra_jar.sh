
mvn deploy:deploy-file \
        -Dfile=jfl-1.6.1.jar \
        -DgroupId=net.neurotech \
        -DartifactId=jfl \
        -Dversion=1.6.1 \
        -Dpackaging=jar \
        -DrepositoryId=fuelquest \
        -Durl=file:///${TOMCAT_HOME}/webapps/maven2

