

ftpdir=$HOME/../ftp

cd ${ftpdir}

ftp -v -n <<EOF
   open "66.235.217.107"
   user "alwaysne" "always888"
   prompt off
   cd public_html/luke_ma/mba/TA
   bin
   get mba_latest.jar
   bye
EOF

mkdir -p ${ftpdir}/tmp

   cd ${ftpdir}/tmp

      rm -rf ${ftpdir}/tmp/mba

      jar xvf ../mba_latest.jar

      rm -rf $HOME/java/mba_1

      mv $HOME/java/mba $HOME/java/mba_1

      mv ${ftpdir}/tmp/mba $HOME/java/mba

      cd $HOME/java/mba
         chmod u+x *sh d n get c* m* ff show o*


