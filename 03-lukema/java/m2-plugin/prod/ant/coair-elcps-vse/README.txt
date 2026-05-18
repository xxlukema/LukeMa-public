
1. Put these lines into ~/.m2/settings:

      <pluginGroups>
         <pluginGroup>com.myproject.plugin.java</pluginGroup>

         <pluginGroup>com.myproject.plugin.ant</pluginGroup>
      </pluginGroups>

2. To build:

      mvn install

3. To run:

      mvn backup:backup

