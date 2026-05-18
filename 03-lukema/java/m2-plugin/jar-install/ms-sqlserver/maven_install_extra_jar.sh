
mvn deploy:deploy-file -Dfile=mssqlserver.jar -DgroupId=com.microsoft.jdbc.sql -DartifactId=mssqlserver -Dversion=2.2 -Dpackaging=jar -DrepositoryId=coair -Durl=file:///myRemoteServerURL
mvn deploy:deploy-file -Dfile=msbase.jar -DgroupId=com.microsoft.jdbc.base -DartifactId=msbase -Dversion=2.2 -Dpackaging=jar -DrepositoryId=coair -Durl=file:///myRemoteServerURL
mvn deploy:deploy-file -Dfile=msutil.jar -DgroupId=com.microsoft.jdbc.util -DartifactId=msutil -Dversion=2.2 -Dpackaging=jar -DrepositoryId=coair -Durl=file:///myRemoteServerURL

mvn install:install-file -Dfile=sqljdbc4-4.0.jar -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc4 -Dversion=4.0 -Dpackaging=jar

# For USAC RAD
'C:\Users\Xianliu.LukeMa\.m2\repository\com\microsoft\sqlserver\sqljdbc4\4.0\sqljdbc4-4.0.jar'	rad		Build path	Build Path Problem
