

echo
echo
echo
echo

if [ $# -ne 1 ]
then
   echo "   Usage $0 classname"
   echo
   exit
fi

pattern=$1



   for i in `find . -name "*.jar"`
   do
      if [ -f $i ]
      then
         jar tvf $i | grep "${pattern}" 
         if [ $? -eq 0 ]
         then
            echo $i
         fi
      fi
   done

echo
echo
echo

