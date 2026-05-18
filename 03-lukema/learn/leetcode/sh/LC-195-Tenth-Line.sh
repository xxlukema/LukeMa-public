# LC - 195 - Tenth Line
# Easy

# The -r option passed to read command prevents backslash escapes from being interpreted.
# Add IFS= option before read command to prevent leading/trailing whitespace from being trimmed.

# Runtime: 151 ms, faster than 5.56% of Bash online submissions for Tenth Line.
# Memory Usage: 3.6 MB, less than 78.74% of Bash online submissions for Tenth Line.


all=""

ctr=0

while IFS= read -r line || [ -n "$line" ]
do
    ctr=$(expr $ctr + 1)

    if [ $ctr -eq 10 ]
    then
        echo $line
    fi

    # str=$(echo $line)
    # all="${all} ${str}"

done < file3.txt

# echo ${all}


