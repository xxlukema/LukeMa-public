
    * svn co http://svn.codehaus.org/mojo/trunk/mojo/xdoclet-maven-plugin
    * cd xdoclet-maven-plugin
    * mvn install
#   * mvn deploy -DaltDeploymentRepository=fuelquest::default::file:///${TOMCAT_HOME}/webapps/maven2

            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>xdoclet-maven-plugin</artifactId>
                <version>1.0-beta-1-SNAPSHOT</version>

                ...
            </plugin>


