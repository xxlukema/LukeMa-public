# sql="insert into country (id, code, name) values ($id, $code, $name);"

i=1
while IFS= read -r line
do
  code=`echo $line | cut -d "," -f 1`
  name=`echo $line | cut -d "," -f 2`

  sql="insert into country (id, code, name) values ($i, '$code', '$name');"
  echo $sql

  i=`expr $i + 1`
done < countries.data
