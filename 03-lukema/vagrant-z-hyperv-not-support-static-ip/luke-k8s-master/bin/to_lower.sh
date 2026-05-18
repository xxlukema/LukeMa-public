

if [ $# -eq 0 ]
then
   exit 1
fi

args=$*

echo "$args" | tr '[:upper:]' '[:lower:]' 


