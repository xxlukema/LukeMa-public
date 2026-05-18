

dir=jasperreports-maven-plugin

if [ -d ${dir} ]
then
   cd ${dir}
   mvn clean deploy -DaltDeploymentRepository=fuelquest::default::file://${TOMCAT_HOME}/webapps/maven2
else
   echo
   echo "   ${dir} not found. Download the plugin first."
   echo
fi
