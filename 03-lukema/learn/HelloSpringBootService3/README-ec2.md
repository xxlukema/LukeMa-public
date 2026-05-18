# EC2 for shein/hello eBay

## AWS

Luke-key
c:/D/Tools/Luke-key-2024-02-02.ppk

i-03e27b1c98b9c2d5d (hello-shein)
PublicIPs: 52.3.85.231    PrivateIPs: 172.31.86.122

<ec2-user@ec2-52-3-85-231.compute-1.amazonaws.com>
<https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/putty.html>

### Configure putty

1. putty ip: <ec2-user@ec2-52-3-85-231.compute-1.amazonaws.com>
2. Connection :: SSH :: Auth :: Credentials :: Private key file for authentication: Browse for the generated `Luke-key-2024-02-02.ppk` file

### Configure WinSCP

## Build docker image

    # step 1. create war file
    m clean -P linux package

    # step 2. docker login
    docker login --username xxlukema/Cfg
    docker login -u xxlukema -p CfgLs-2020dc

    # step 3. docker buildx build
    # v0.0.2 logs to 'stdout/stderr'
    docker buildx build . -t xxlukema/hello-shein-boot:0.0.2
    
    # step 4. docker push
    # optional if run docker-compose, required if deploy to k8s:
    docker push xxlukema/hello-shein-boot:0.0.2
