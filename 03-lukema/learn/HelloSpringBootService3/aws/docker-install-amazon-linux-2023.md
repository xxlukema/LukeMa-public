# Installing Docker on AL2023

[Installing Docker on AL2023]<https://docs.aws.amazon.com/AmazonECS/latest/developerguide/create-container-image.html>

## Install

    # 1. update
    sudo yum update -y

    # 2. install
    sudo yum install docker -y

    # 3. enable/status/start/restart/stop
    sudo systemctl enable docker
    sudo systemctl status docker
    sudo systemctl start docker
    sudo systemctl restart docker
    sudo systemctl stop docker

    # 4. Add the `ec2-user` to the docker group so you can execute Docker commands without using `sudo`.
    sudo usermod -a -G docker ec2-user

    # 5. install `docker-compose`
    sudo curl -sL https://github.com/docker/compose/releases/latest/download/docker-compose-linux-x86_64 -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
    sudo chmod a+rwx /var/run/docker.sock
    # verify 
    docker-compose --version
    > Docker Compose version v2.24.5

    # info
    docker info
