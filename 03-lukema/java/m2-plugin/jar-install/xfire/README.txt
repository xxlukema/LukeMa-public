
               READ ME

0. It needs jta-1.0.1B

1. Put settings.xml into ~/.m2 directory.

2. To use microsoft sql server package, put these lines into the 
   project's pom.xml file:
   

   </project>
   
     ...
   
     <repositories>
       <repository>
         <id>luke.home</id>
         <url>file:///myRemoteServerURL</url>
       </repository>
     </repositories>
     ...
   
   </project>
   
   Or in ~/.m2/settings.xml, put:

   <profiles>
      <profile>
         <id>myprofile</id>

         <repositories>
            <repository>
               <id>luke.home</id>
               <name>costom remote repo</name>
               <url>file:///myRemoteServerURL</url>
            </repository>
         </repositories>
      </profile>
   </profiles>

   <activeProfiles>
      <activeProfile>coair</activeProfile>
   </activeProfiles>


   where the id 'luke.home' is the name/alias of the remote repository. 
   The name/alias of the remote repository 
   http://www.ibiblio.org/maven2/ is 'central'.



