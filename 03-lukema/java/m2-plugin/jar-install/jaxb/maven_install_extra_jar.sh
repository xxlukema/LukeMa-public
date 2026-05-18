

ROOT=../../../../..

JAXB_HOME=$ROOT/01-LukeTools/Sun/jwsdp-2.0/jaxb

# mvn deploy:deploy-file -Dfile=$JAXB_HOME/lib/jaxb-api.jar -DgroupId=javax.xml -DartifactId=jaxb-api -Dversion=2.0 -Dpackaging=jar -DrepositoryId=jwsdp-2.0 -Durl=file:///${TOMCAT_HOME}/webapps/maven2

# mvn deploy:deploy-file -Dfile=$JAXB_HOME/lib/jaxb-impl.jar -DgroupId=javax.xml -DartifactId=jaxb-impl -Dversion=2.0 -Dpackaging=jar -DrepositoryId=jwsdp-2.0 -Durl=file:///${TOMCAT_HOME}/webapps/maven2

# mvn deploy:deploy-file -Dfile=$JAXB_HOME/lib/jaxb-xjc.jar -DgroupId=javax.xml -DartifactId=jaxb-xjc -Dversion=2.0 -Dpackaging=jar -DrepositoryId=jwsdp-2.0 -Durl=file:///${TOMCAT_HOME}/webapps/maven2

# mvn deploy:deploy-file -Dfile=$JAXB_HOME/lib/jaxb1-impl.jar -DgroupId=javax.xml -DartifactId=jaxb1-impl -Dversion=2.0 -Dpackaging=jar -DrepositoryId=jwsdp-2.0 -Durl=file:///${TOMCAT_HOME}/webapps/maven2

# mvn deploy:deploy-file -Dfile=$JAXB_HOME/../sjsxp/lib/jsr173_api.jar -DgroupId=javax.xml -DartifactId=jsr173_api -Dversion=2.0 -Dpackaging=jar -DrepositoryId=stream-2.0 -Durl=file:///${TOMCAT_HOME}/webapps/maven2


mvn install:install-file -DgroupId=com.sun.jmx -DartifactId=jmxri -Dversion=1.2.1 -Dpackaging=jar -Dfile=jmxri-1.2.1.jar
mvn install:install-file -DgroupId=com.sun.jdmk -DartifactId=jmxtools -Dversion=1.2.1 -Dpackaging=jar -Dfile=jmxtools-1.2.1.jar


# mvn deploy:deploy-file \
#         -Dfile=${JAXB_HOME}/lib/jsr181-api.jar \
#         -DgroupId=javax.jws \
#         -DartifactId=jsr181-api \
#         -Dversion=1.0 \
#         -Dpackaging=jar \
#         -DrepositoryId=fuelquest \
#         -Durl=file:///${TOMCAT_HOME}/webapps/maven2

