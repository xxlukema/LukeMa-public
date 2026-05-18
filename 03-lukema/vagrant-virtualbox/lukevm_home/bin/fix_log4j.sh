

files=`find . -name log4j.xml`

tmp=$HOME/../ftp/log4j.xml

for file in $files
do
   cat $file | sed "s/hiberate/hibernate/g" > $tmp
   cp $tmp $file

   echo "Fixed $file"
done


