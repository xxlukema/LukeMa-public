# EC2

## AWS

Luke-key
c:/D/Tools/Luke-key-2024-02-02.ppk

i-03e27b1c98b9c2d5d (hello-shein)
PublicIPs: 52.3.85.231    PrivateIPs: 172.31.86.122

<ec2-user@ec2-52-3-85-231.compute-1.amazonaws.com>
<https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/putty.html>

## Build docker image

    # step 1. build dist
    npm run build

    # step 2. docker login
    docker login --username xxlukema/Cfg-erlingerlingdc
    docker login -u xxlukema --password-stdin

    # step 3. docker buildx build
    # v0.0.2 logs to 'stdout/stderr'
    docker buildx build . -t xxlukema/hello-shein-web:0.0.2
    
    # step 4. docker push
    # optional if run docker-compose, required if deploy to k8s:
    docker push xxlukema/hello-shein-web:0.0.2
