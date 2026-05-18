
mvn deploy:deploy-file -Dfile=com.ibm.mq.jar -DgroupId=com.ibm.mq -DartifactId=com.ibm.mq -Dversion=20060425 -Dpackaging=jar -DrepositoryId=coair -Durl=file:///myRemoteServerURL
mvn deploy:deploy-file -Dfile=com.ibm.mqjms.jar -DgroupId=com.ibm.mq -DartifactId=com.ibm.mqjms -Dversion=20060425 -Dpackaging=jar -DrepositoryId=coair -Durl=file:///myRemoteServerURL
mvn deploy:deploy-file -Dfile=xsd.bean.runtime.jar -DgroupId=com.ibm.websphere -DartifactId=xsd.bean.runtime -Dversion=20060425 -Dpackaging=jar -DrepositoryId=coair -Durl=file:///myRemoteServerURL

