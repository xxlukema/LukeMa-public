
src=Eximfa

dest=Eximfa-good


path=/cygdrive/d/00-AppServers/apache-tomcat-7.0.53/webapps


files=`cat ../files.txt `

for file in $files
do
   # echo $file

   diff $file ../$dest/$file > /dev/null 2>&1

   if [ $? -eq 1 ]
   then
      echo "###### $file ######"
   fi

done


