
if [ $# -ne 2 ]
then
   echo
   echo Usage: $0 artifactId ver
   echo

   exit 1
fi


artifactId=$1
ver=$2

groupId=slb.rti
repositoryId=luke

ROOT=../../..
LIB=${ROOT}/slb-jars

jarFile=${LIB}/${artifactId}_${ver}.jar



type=jar
url=file:///${TOMCAT_HOME}/webapps/maven2

maven_deploy.sh ${jarFile} ${artifactId} ${ver} ${groupId} ${type} ${repositoryId} ${url} 

