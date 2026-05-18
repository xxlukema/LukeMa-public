
Resource Bundle:

How it works?

Example 1
A normal way to access the message.

<h:outputText value="#{msg.message}" />
 
//properties file
message = This is "message"

Example 2
For a key that has a dot “.” as name, you can’t use the normal way #{msg.message.test1}, it will not work. Instead, you should use bracket like #{msg['message.test1']}.

<h:outputText value="#{msg['message.test1']}" />
 
//properties file
message.test1 = This is "message.test1"

Example 3
To display HTML tag in the message, just add the “escape” attribute and set it to false.

<h:outputText value="#{msg['message.test2']}" />
<h:outputText value="#{msg['message.test2']}" escape="false" />
<h:outputText value="#{msg['message.test3']}" />
<h:outputText value="#{msg['message.test3']}" escape="false" />
 
//properties file
message.test2 = This is "<h2>message.test3</h2>"
message.test3 = This is "&lt;h2&gt;message.test4&lt;/h2&gt;"

Example 4
For a parameter message, just use the <h:outputFormat /> and <f:param / > tag.

<h:outputFormat value="#{msg['message.param1']}">
   <f:param value="param0" />
</h:outputFormat>
<h:outputFormat value="#{msg['message.param2']}">
   <f:param value="param0" />
   <f:param value="param1" />
</h:outputFormat>
 
//properties file
message.param1 = This is "message.param1" - {0}
message.param2 = This is "message.param2" - {0} and {1}

