# LC - 193 - Valid Phone Numbers

# Runtime: 151 ms, faster than 13.77% of Bash online submissions for Valid Phone Numbers.
# Memory Usage: 3.1 MB, less than 55.57% of Bash online submissions for Valid Phone Numbers.

grep -e "^[0-9]\{3\}-[0-9]\{3\}-[0-9]\{4\}$" -e "^([0-9]\{3\}) [0-9]\{3\}-[0-9]\{4\}$" file.txt


