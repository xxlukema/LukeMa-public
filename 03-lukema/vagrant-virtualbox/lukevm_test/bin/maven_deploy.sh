
if [ $# -ne 7 ]
then
   echo
   echo Usage: $0 jarFile artifactId ver groupId type repositoryId url
   echo

   exit 1
fi


jarFile=$1
artifactId=$2
ver=$3

groupId=$4
type=$5
repositoryId=$6
url=$7


mvn deploy:deploy-file \
        -Dfile=${jarFile} \
        -DgroupId=${groupId} \
        -DartifactId=${artifactId} \
        -Dversion=${ver} \
        -Dpackaging=${type} \
        -DrepositoryId=${repositoryId} \
        -Durl=${url}

