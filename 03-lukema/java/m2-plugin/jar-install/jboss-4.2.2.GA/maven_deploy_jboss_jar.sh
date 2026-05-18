
mvn deploy:deploy-file \
        -Dfile=${JBOSS_HOME}/lib/jboss-system.jar \
        -DgroupId=jboss \
        -DartifactId=jboss-system \
        -Dversion=${JBOSS_VERSION} \
        -Dpackaging=jar \
        -DrepositoryId=fuelquest \
        -Durl=file:///${TOMCAT_HOME}/webapps/maven2

mvn deploy:deploy-file \
        -Dfile=${JBOSS_HOME}/server/all/lib/jboss.jar \
        -DgroupId=jboss \
        -DartifactId=jboss \
        -Dversion=${JBOSS_VERSION} \
        -Dpackaging=jar \
        -DrepositoryId=fuelquest \
        -Durl=file:///${TOMCAT_HOME}/webapps/maven2

mvn deploy:deploy-file \
        -Dfile=${JBOSS_HOME}/server/all/lib/jbossha.jar \
        -DgroupId=jboss \
        -DartifactId=jbossha \
        -Dversion=${JBOSS_VERSION} \
        -Dpackaging=jar \
        -DrepositoryId=fuelquest \
        -Durl=file:///${TOMCAT_HOME}/webapps/maven2

mvn deploy:deploy-file \
        -Dfile=${JBOSS_HOME}/lib/jboss-jmx.jar \
        -DgroupId=jboss \
        -DartifactId=jboss-jmx \
        -Dversion=${JBOSS_VERSION} \
        -Dpackaging=jar \
        -DrepositoryId=fuelquest \
        -Durl=file:///${TOMCAT_HOME}/webapps/maven2

mvn deploy:deploy-file \
        -Dfile=${JBOSS_HOME}/lib/jboss-common.jar \
        -DgroupId=jboss \
        -DartifactId=jboss-common \
        -Dversion=${JBOSS_VERSION} \
        -Dpackaging=jar \
        -DrepositoryId=fuelquest \
        -Durl=file:///${TOMCAT_HOME}/webapps/maven2

mvn deploy:deploy-file \
        -Dfile=${JBOSS_HOME}/server/all/lib/jboss-j2ee.jar \
        -DgroupId=jboss \
        -DartifactId=jboss-j2ee \
        -Dversion=${JBOSS_VERSION} \
        -Dpackaging=jar \
        -DrepositoryId=fuelquest \
        -Durl=file:///${TOMCAT_HOME}/webapps/maven2

mvn deploy:deploy-file \
        -Dfile=${JBOSS_HOME}/server/all/lib/jnpserver.jar \
        -DgroupId=jboss \
        -DartifactId=jnpserver \
        -Dversion=${JBOSS_VERSION} \
        -Dpackaging=jar \
        -DrepositoryId=fuelquest \
        -Durl=file:///${TOMCAT_HOME}/webapps/maven2

