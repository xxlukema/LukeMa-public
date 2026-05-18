

ARGS=`echo "$*" | tr '[:upper:]' '[:lower:]'`

if [ $# -gt 0 -a "$ARGS" != "copy" ]
then
   echo
   echo " Usage: $0 [copy] "
   echo

   exit 1
fi 




branch=11.6.17


ws=$HOME/../workspace
wd=$ws/$branch


pwd=`pwd`
app=`basename $pwd`

dir=$wd/applications/$app


do_compare()
{
   echo "Checking $1 files..."

   for i in `find . -name "$1" | grep -v "/target/" | grep -v "/test/" | grep -v pom.xml | grep -v build-luke.xml | grep -v build.xml`
   do
      copy=0
      if [ ! -f $dir/$i ]
      then 
         echo "New: $i"
         copy=1
      else
         diff $i $dir/$i > /dev/null
         if [ $? -eq 1 ]
         then
            echo "diff: $i"
            copy=1
         fi
      fi

      if [ "$ARGS" = "copy" -a $copy -eq 1 ]
      then
         cp $i $dir/$i
      fi
   done
}


files="*.java"
do_compare "$files"

files="*.xml"
do_compare "$files"

