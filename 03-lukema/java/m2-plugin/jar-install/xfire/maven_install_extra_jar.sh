
ROOT=../../../../..

# mvn install:install-file -Dfile=$ROOT/01-LukeTools/Sun/SDK/lib/j2ee.jar         -DgroupId=javax.j2ee -DartifactId=j2ee -Dversion=1.5 -Dpackaging=jar 

# mvn install:install-file -Dfile=$ROOT/01-LukeTools/Sun/SDK/lib/activation.jar   -DgroupId=javax.activation -DartifactId=activation -Dversion=1.1 -Dpackaging=jar

# mvn install:install-file -Dfile=$ROOT/01-LukeTools/Sun/SDK/lib/mail.jar         -DgroupId=javax.mail -DartifactId=mail -Dversion=1.5 -Dpackaging=jar

mvn install:install-file -Dfile=jaxm-api.jar     -DgroupId=javax.xml -DartifactId=jaxm-api -Dversion=1.1.2 -Dpackaging=jar

mvn install:install-file -Dfile=saaj-api.jar     -DgroupId=javax.xml -DartifactId=saaj-api -Dversion=1.3 -Dpackaging=jar

mvn install:install-file -Dfile=jta-1_0_1B-classes.zip  -DgroupId=javax.transaction -DartifactId=jta -Dversion=1.0.1B -Dpackaging=jar



