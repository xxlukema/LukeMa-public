
WEBLOGIC_LIB_DIR=../../../../../bea/weblogic81/server/lib

mvn deploy:deploy-file -Dfile=${WEBLOGIC_LIB_DIR}/weblogic.jar -DgroupId=bea -DartifactId=weblogic -Dversion=8.1 -Dpackaging=jar -DrepositoryId=jpmc -Durl=file:///myRemoteServerURL

mvn deploy:deploy-file -Dfile=${WEBLOGIC_LIB_DIR}/webservices.jar -DgroupId=bea -DartifactId=webservices -Dversion=8.1 -Dpackaging=jar -DrepositoryId=jpmc -Durl=file:///myRemoteServerURL




