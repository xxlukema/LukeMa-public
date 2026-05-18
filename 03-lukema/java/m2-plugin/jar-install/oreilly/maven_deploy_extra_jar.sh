
mvn deploy:deploy-file \
        -Dfile=cos.jar \
        -DgroupId=com.oreilly \
        -DartifactId=cos \
        -Dversion=20070803 \
        -Dpackaging=jar \
        -DrepositoryId=fuelquest \
        -Durl=file:///${TOMCAT_HOME}/webapps/maven2

