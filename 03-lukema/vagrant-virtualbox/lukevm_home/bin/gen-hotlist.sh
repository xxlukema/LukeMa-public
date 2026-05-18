
line3="                  </h:commandLink>"


for symbol in `cat hotlist.txt`
do

line1="                  <h:commandLink value=\"${symbol}\" actionListener=\"#{taController.symbolLinkClickedActionListener}\" action=\"#{taController.getTaChart}\">"
line2="                     <f:attribute name=\"#{taController.symbolString}\" value=\"${symbol}\" />"

   echo $line1
   echo $line2
   echo $line3
   echo 
done




