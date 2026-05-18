FROM openjdk:19-jdk-alpine3.16

EXPOSE 8443

# Do not run mvn build in docker buildx build. Unable to find mvn command.
# run: mvn -P AWS-Docker -Dmaven.test.skip=true clean package

ADD target/my-properties-boot.war my-properties-boot.war

RUN mkdir -p /home/ec2-user/dockerlogs

# The VOLUME instruction does not support specifying a host-dir parameter.
# You must specify the mountpoint when you create or run the container.
# VOLUME /host-path /docker-path
# VOLUME host-value-name /docker-path
# VOLUME /home/ec2-user/dockerlogs-host /home/ec2-user/dockerlogs

ENTRYPOINT ["java", "-jar", "my-properties-boot.war"]
