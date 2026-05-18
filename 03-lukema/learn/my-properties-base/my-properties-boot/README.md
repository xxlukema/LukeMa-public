
# `my-properties-boot` Dockerfile-alpine-boot-standalone

## Swagger/OpenAPI Problems (my-properties-boot)

    # 1. As of 12/19/2022, boot 3 + Swagger does not start.
    # 2. As of 12/19/2022, boot 3 + OpenAPI works on Windows with vscode.
    # 3. As of 12/19/2022, boot 3 + OpenAPI throw the following error when open swagger-ui on docker:
    # Failed to load API definition.
    # Errors
    # Fetch error
    # response status is 500 /my-properties-boot/v3/api-docs

## ALWAYS run `mvn clean package` for local development using `spring-boot-devtools`

This project is profile based. Need to run `mvn clean package` often.

    # !! Important:
    # ALWAYS run on local development using `spring-boot-devtools` (Otherwise, OpenAPI will not work):
    mvn clean package

    # logs to 'stdout/stderr'
    # mvn -P fluentd -Dmaven.test.skip=true clean package
    # logs to 'home/lma/logs/my-properties-boot.log'
    # mvn -P k8s -Dmaven.test.skip=true clean package
    #
    # mvn -P AWS-ec2-Docker -Dmaven.test.skip=true clean package
    # mvn -P laptop -Dmaven.test.skip=true clean package

    # xxlukema/Cfg-
    docker login --username xxlukema
    # 
    # v0.0.2 logs to 'stdout/stderr'
    mvn -P fluentd -Dmaven.test.skip=true clean package
    docker buildx build . -f Dockerfile-alpine -t xxlukema/my-properties-boot:0.0.2
    docker push xxlukema/my-properties-boot:0.0.2
    #
    # v0.0.1 logs to 'home/lma/logs/my-properties-boot.log'
    mvn -P k8s -Dmaven.test.skip=true clean package
    docker buildx build . -f Dockerfile-alpine -t xxlukema/my-properties-boot:0.0.1
    docker push xxlukema/my-properties-boot:0.0.1

## `spring-boot-devtools` Might no Work for Profile Base Project. Run `mvn clean package`

## Run Server

    # run boot
    mvn spring-boot:run
    # test short
    curl -k -i -X GET https://localhost:8443/my-properties-boot/rest/ping
    #
    curl -k -i -X GET https://localhost:8443/my-properties-boot/rest/house/getDateUpdated
    # test long
    curl -k -i -X GET https://localhost:8443/my-properties-boot/rest/house/getPropertyList

    # run war
    java -jar target/my-properties-boot.war
    
    # set 'ver' as windows host environment varaiable
    set ver=1.0
    docker buildx build -t xxlukema/my-properties-boot:%ver% -f Dockerfile-alpine-boot-standalone .
    
    docker login -u xxlukema [-p CfgLs- ]
    docker push xxlukema/my-properties-boot:%ver%
    
    # set 'ver' as linux host environment varaiable
    export ver=1.0
    docker image pull xxlukema/my-properties-boot:${ver}
    sudo docker run -dp 8443:8443 --name my-properties-boot   \
                    -v /home/ec2-user/dockerlogs-host:/home/ec2-user/dockerlogs  \
                    xxlukema/my-properties-boot:${ver}
    curl -k -i -X GET https://localhost:8443/my-properties-boot/actuator/health
    docker exec -it my-properties-boot cat /home/ec2-user/dockerlogs/my-properties-boot.log
    docker exec -it my-properties-boot ls -l /home
    docker inspect my-properties-boot
    
    mvn -Dmaven.test.skip=true compile
    
    docker container prune -f
    docker image prune -f
    docker volume prune -f
    
    # latest
    sudo docker buildx build -t marc/test .
    sudo docker tag marc/test marc/test:1.0.1

## On laptop

    server.port: 8080
    server.servlet.context-path: /
    run on: stand-alone boot

## On EC2 Tomcat

    server.port: 8080
    server.servlet.context-path: /my-properties-boot
    TOMCAT_HOME: /home/ec2-user/apache-tomcat-9.0.41 

## On EC2 Docker

    server.port: 8443
    server.servlet.context-path: /my-properties-boot
    container: docker
    run on: stand-alone boot

## Run Local

    Alt + F5
    Or
    Eclipse Menu --> Project --> Clean...

## build my-properties-boot for AWS

    # Build for AWS.
    # Use 'clean' for a clean AWS war file.
    
    # tomcat app logs to ${TOMCAT_HOME}/logs/my-properties-boot
    # Deployed on ec2 tomcat server
    # port: 8080
    mvn -P AWS-Tomcat -Dmaven.test.skip=true clean package
    
    # docker logs to mounted dir /home/ec2-user/dockerlogs/my-properties-boot
    # EXPOSE 8443
    # alpine, apache
    mvn -P AWS-ec2-Docker -Dmaven.test.skip=true clean package
    
    # Build docker image for my-properties-boot.
    docker buildx build -t xxlukema/my-properties-boot:%ver% -f Dockerfile .
    
    # Push docker image to repo
    docker login -u xxlukema [-p CfgLs- ]
    docker push xxlukema/my-properties-boot:%ver%
    
    # Recover target/classes to run test on local.
    # Do not use 'clean' to keep AWS build war.
    mvn -Dmaven.test.skip=true compile

## Deploy my-properties-boot to AWS

    sudo -s
    sudo docker image ls
    sudo docker login -u xxlukema [-p CfgLs- ]
    export ver=1.0
    sudo docker image pull xxlukema/my-properties-boot:${ver}
    
    # Create volume
    # sudo docker volume create dockerlogs
    # sudo docker volume ls
    # sudo docker volume inspect dockerlogs

    # -p 80:8080, where 80 is the exposed EC2 http/https port, and 8080 is the spring boot port inside docker container.
    # -p 90:8443, where 90 is the exposed EC2 http/https port, and 8443 is the spring boot port inside docker container.
    # sudo docker run -dp 8443:8443 --name=my-properties-boot xxlukema/my-properties-boot
    sudo docker run -dp 8443:8443 --name my-properties-boot xxlukema/my-properties-boot
    curl -k -i -X GET https://localhost:8443/my-properties-boot/actuator/health
    docker exec -it my-properties-boot cat /home/ec2-user/dockerlogs/my-properties-boot.log
    sudo docker ps

## [Multiple http ports on single EC2](https://medium.com/finnovate-io/deployment-multiple-services-on-a-single-instance-of-aws-elastic-beanstalk-f9bc00908c64)

    # 1. Create a named value: dockerlogs
    sudo docker volume create dockerlogs
    
    # 2. 
    docker run --name=nginx-contaner-name -d -v /host-path:/container-path -p 5000:8443 nginx-image-name
    
    --name=nginx-contaner-name        name of the container so we can refer to it more easily.
    -d                                detaches the process and runs it in the background.
    
    -v /host-path:/container-path     If the first argument begins with a / or ~/, you are creating a bindmount.
    -v volume-name:/container-path    Creates a volume named volume-name with no relationship to the host.
    
    -p 5000:8443                      host-port:docker-expose-port
    nginx-image-name                  name of the image

## Notes

    # This will not work, because args after image name are considered "image args". So it will not bind.
    # sudo docker run -dp 8443:8443 --name my-properties-boot xxlukema/my-properties-boot:${ver}  \
    #                 -v /home/ec2-user/dockerlogs-host:/home/ec2-user/dockerlogs
    #
    # sudo docker run -dp 8443:8443 --name my-properties-boot xxlukema/my-properties-boot:${ver}
