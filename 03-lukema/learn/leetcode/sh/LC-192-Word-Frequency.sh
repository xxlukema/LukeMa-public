# LC - 192 - Word Frequency
# Medium


words=$(cat words.txt | sed 's/ /\n/g' | sort -r | uniq)

for i in $words
do
   echo $i $(cat words.txt | sed "s/ /\n/g" | grep -w $i | wc -l)
done

