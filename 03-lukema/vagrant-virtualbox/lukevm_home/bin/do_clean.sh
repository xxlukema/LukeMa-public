

proj=`cat ~/.bin/do_clean.txt`

cd ~/../workspace

for i in $proj
do
   rm -f `find $i -name .project`
   rm -rf `find $i -name .settings`
   rm -f `find $i -name .classpath`
   rm -rf `find $i -name target`
done




