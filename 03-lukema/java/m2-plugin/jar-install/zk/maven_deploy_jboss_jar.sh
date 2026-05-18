

wd=/cygdrive/d/01-LukeTools/zk-lib-5.0.2

groupId=org.zk
version=5.0.2
repositoryId=apptricity
url=file:///${TOMCAT_HOME}/webapps/maven2


if [ ! -d $wd ]
then
   echo 
   echo ERROR: Not found $wd
   echo
   exit
fi


cd $wd

   for file in `ls *.jar`
   do
      artifactId=`echo $file | sed "s/\.jar$//"`

      mvn deploy:deploy-file -Dfile=${file} -DgroupId=${groupId} -DartifactId=${artifactId} -Dversion=${version} -Dpackaging=jar -DrepositoryId=${repositoryId} -Durl=${url}


      # echo "<dependency> <groupId>${groupId}</groupId> <artifactId>${artifactId}</artifactId> "
      # echo "<version>${version}</version> </dependency>"

   done

exit



