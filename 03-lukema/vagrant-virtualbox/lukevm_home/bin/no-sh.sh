

for i in `find . -name "*.sh"`
do
   if [ -f $i ]
   then

      # If the file is already in sh dir, do nothing. else,
      # create sh dir and move the file into sh dir.


      dir=`dirname $i`

      dir_base=`basename $dir`

      if [ "${dir_base}" != "sh" ]
      then

         if [ ! -d ${dir}/sh ]
         then
            mkdir -p ${dir}/sh
         fi

         mv $i ${dir}/sh
      fi
   fi
done



