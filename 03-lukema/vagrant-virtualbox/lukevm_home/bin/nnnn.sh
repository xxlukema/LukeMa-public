

for i in `find . -name "*.sh"`
do
   if [ -f $i ]
   then
      dir_sh=`dirname $i`
      dir_b=`dirname ${dir_sh}`
      sh=`basename ${dir_sh}`

      if [ "${sh}" = "sh" ]
      then
         mv $i ${dir_b}
      fi
   fi
done












