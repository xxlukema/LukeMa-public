# Keycloak 17.0.0

## Guide
[Guide:server]<https://www.keycloak.org/guides#server>

    docker exec -it keycloak-lma sh -c "cat ~/conf/keycloak.conf" > keycloak.conf

## TLS

[Guild:tls]<https://www.keycloak.org/server/enabletls>
[Gen crt]<https://stackoverflow.com/questions/10175812/how-to-generate-a-self-signed-ssl-certificate-using-openssl>

- 1. default port: `8443`

- 2. default keystore password: `password`

## Connect to Localhost Within a Docker Container

[Connect to Localhost Within a Docker Container]<https://www.cloudsavvyit.com/14114/how-to-connect-to-localhost-within-a-docker-container/>

### The Easy Option - User `host.docker.internal` for Windows or Mac

Docker Desktop 18.03+ for Windows and Mac supports `host.docker.internal` as a functioning alias for localhost. Use this string inside your containers to access your host machine.

- 1. `localhost` and `127.0.0.1` – These resolve to the container.
- 2. `host.docker.internal` – This resolves to the outside host. Ex. MySQL `host.docker.internal:3306`

### For Linux - Enable `host.docker.internal` the `--add-host` flag for docker run

    docker run -d --add-host host.docker.internal:myhost my-container:latest
    
The `--add-host` flag adds an entry to the container `/etc/hosts` file. The value shown above maps `host.docker.internal` to the container host gateway,
which matches the real `localhost` value. You could replace `host.docker.internal` with your own string if you prefer.

### Connecting to the Host Network

### Accessing the Host With the Default Bridge Mode

    ip addr show docker0


