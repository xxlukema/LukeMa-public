
# my-properties-base

    docker container stop tomcat
    docker container rm tomcat
    docker run --name tomcat -dp 80:80 tomcat
    
    docker container stop my-properties-base
    docker container rm my-properties-base
    
    # Inside /my-properties-boot
    mvn -P AWS-eb-Docker -Dmaven.test.skip=true clean package
    
    # Inside /my-properties-ng
    npm run build
    
    docker buildx build -t xxlukema/my-properties-base:%ver% -f Dockerfile .
    
    docker push xxlukema/my-properties-base:%ver%
    
    docker run --name my-properties-base -dp 80:8080  \
               -v /home/ec2-user/tomcat-logs:/usr/local/tomcat/logs  \
               xxlukema/my-properties-base:${ver}

    docker run --name my-properties-base -dp 80:8080 xxlukema/my-properties-base:%ver%
    
    docker exec -it tomcat cat /usr/local/tomcat/conf/server.xml | grep 80
