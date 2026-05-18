

ROOT=../../../../..

# mvn deploy:deploy-file -Dfile=../../../../../bea/weblogic81/server/lib/weblogic.jar -DgroupId=bea -DartifactId=weblogic -Dversion=8.1 -Dpackaging=jar -DrepositoryId=jpmc -Durl=file:///myRemoteServerURL
# mvn deploy:deploy-file -Dfile=../../../../../bea/weblogic81/server/lib/webservices.jar -DgroupId=bea -DartifactId=webservices -Dversion=8.1 -Dpackaging=jar -DrepositoryId=jpmc -Durl=file:///myRemoteServerURL


#mvn deploy:deploy-file -Dfile=../../../../../bea-alpha/weblogic.jar -DgroupId=bea -DartifactId=weblogic -Dversion=8.1 -Dpackaging=jar -DrepositoryId=jpmc -Durl=file:///myRemoteServerURL
mvn deploy:deploy-file -Dfile=../../../../../bea-alpha/webservices.jar -DgroupId=bea -DartifactId=webservices -Dversion=8.1 -Dpackaging=jar -DrepositoryId=jpmc -Durl=file:///myRemoteServerURL


mvn install:install-file -DgroupId=com.jpmorgan -DartifactId=coastAMPCommon -Dversion=1.0 -Dpackaging=jar -Dfile=$ROOT/workspace/collateral_web/WebContent/WEB-INF/lib/coastAMPCommon.jar


mvn install:install-file -DgroupId=com.jpmorgan -DartifactId=coastAMPClient -Dversion=1.0 -Dpackaging=jar -Dfile=$ROOT/workspace/collateral_web/WebContent/WEB-INF/lib/coastAMPClient.jar


