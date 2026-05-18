# LC - 194 - Transpose File

# while IFS= read -r line
# do
#     echo $line
# done < file2.txt
# echo $line


line1=""
line2=""

# "cat file2.txt | while read ..." will not work because commands after pipe will be executed in another sub-shell.
# And therefore, the block inside the while cannot modify global variables.
# Therefore, always use "<" to redirect input file.

while read line || [ -n "$line" ]
do
    arr=($(echo $line | tr ' ' '\n'))
    line1="${line1} ${arr[0]}"
    line2="${line2} ${arr[1]}"
done < file2.txt

echo $line1
echo $line2
