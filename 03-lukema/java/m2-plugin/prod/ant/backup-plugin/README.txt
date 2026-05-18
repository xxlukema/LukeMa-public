

Help: google: maven ant mojo plugin 


1. Put these lines into ~/.m2/settings:

      <pluginGroups>
         <pluginGroup>com.learn</pluginGroup>
      </pluginGroups>

2. To build:

      mvn -U clean install

3. To run:

      mvn backup:backup

4. Must be installed using maven-2.2.1

