FROM amazoncorretto:22.0.1-alpine3.19

EXPOSE 8443

# Do not run mvn build in docker buildx build. Unable to find mvn command.
# run: mvn -P k8s -Dmaven.test.skip=true clean package
ADD target/HelloSpringBootService3.war HelloSpringBootService3.war

# Optional
# RUN mkdir -p /home/lma/logs

ENV JASYPT_ENCRYPTOR_PASSWORD luke-key
ENV JASYPT_ENCRYPTOR_ALGORITHM PBEWithMD5AndDES

ENTRYPOINT ["java", "-jar", "HelloSpringBootService3.war"]
