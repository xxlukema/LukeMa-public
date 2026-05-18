# my-properties-ng

## 1. Routing on ec2

Unable to make `nginx` docker image work as a reverse proxy. Therefore, it is needed to install amazon `nginx1`.

### 1.1 Install `nginx`

    # This will install amazon nginx1.
    # Perhaps there is no repo for `sudo yum install nginx`
    sudo amazon-linux-extras install nginx1

### 1.2 Setup Reverse Proxy

    # Create a new file: /etc/nginx/conf.d/ec2.http.conf
    
    server {
        listen       80;
        listen  [::]:80;
        server_name  localhost;
        
        # my stuff /my-properties-ng/
        location /my-properties-ng/ {
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_pass       http://localhost:4200/my-properties-ng/;
        }
        
        # my stuff /my-properties-boot/
        location /my-properties-boot/ {
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_pass       http://localhost:8090/my-properties-boot/;
        }
    }

### 1.3 Verify Reverse Proxy

    curl -k -i -L -X GET https://localhost:8443/
    curl -k -i -L -X GET https://localhost:8443/my-properties-ng/
    curl -k -i -L -X GET https://localhost:8443/my-properties-boot/

### 1.4 Reverse Proxy Routing Map

    :80   -------- nginx is a reverse proxy server.
    :8090 -------- docker image xxlukema/my-properties-boot with spring boot standalone server
    :4200 -------- docker image xxlukema/my-properties-ng with httpd running on centos
    /home/ec2-user/dockerlogs-host ----- spring boot image log directory mount point
    /etc/nginx/conf.d/ec2.http.conf ---- (Not in-use) nginx reverse proxy routing mount point
    /home/ec2-user/nginx-conf/*.conf --- (Not in-use)

## Build my-properties-ng

    npm run build

## Create docker image

    docker login -u xxlukema [-p CfgLs- ]

    # Build docker image for my-properties-ng.
    # set 'ver' as windows host environment varaiable
    set ver=1.0
    docker buildx build -t xxlukema/my-properties-ng:%ver% -f Dockerfile-centos-httpd .
    # Push docker image to repo
    docker push xxlukema/my-properties-ng:%ver%

## Deploy my-properties-ng to ec2

    sudo -s
    sudo docker image ls
    docker login -u xxlukema [-p CfgLs- ]
    export ver=1.0
    docker image pull xxlukema/my-properties-ng:${ver}
    
    sudo docker run -dp 4200:80 --name=my-properties-ng xxlukema/my-properties-ng:${ver}

## Validate

    # ec2
    curl -k -i -X GET http://localhost:8090/my-properties-boot/actuator/health

    # laptop
    export HOST=ec2-52-205-28-223.compute-1.amazonaws.com
    curl -k -i -X GET http://${HOST}/my-properties-boot/actuator/health

## Prevent `CORS` for `localhost`

[Tutorial Video](https://www.samjulien.com/proxy-angular-cli-cors)

    # Example project: learn/my-properties-base

    # 1. Add file src/proxy.conf.json:
    {
        "/my-properties-boot": {
            "target": "https://localhost:8443/",
            "secure": false,
            "logLevel": "info"
        }
    }

    # 2. Change angular.json by adding "proxyConfig": "src/proxy.conf.json":
    ...
    "architect": {
      "serve": {
        "builder": "@angular-devkit/build-angular:dev-server",
        "options": {
          "buildTarget": "angular-application-name:build",
          "proxyConfig": "src/proxy.conf.json"
        },
    ...

    # 3. BootCorsFilter1.java, comment out "Access-Control-Allow-Origin":
    // res.setHeader("Access-Control-Allow-Origin", accessControlAllowOrigin);
    // res.setHeader("Access-Control-Allow-Origin", "'http://localhost:4200' always");

    # 4. Change environment.ts for "https://localhost:8443/my-properties-boot", remove "https://localhost:8443":
    // Comment out this line:
    // this.apiEndpoint = 'http://' + this.localHost + ':8080' + this.bootContextPath;
    // Add this line:
    this.apiEndpoint = this.bootContextPath;

    # 5. (Optional) package.json:
    # If "proxyConfig": "src/proxy.conf.json" had been added to angular.json, then start server with:
    ng serve 
    # Or
    npm run start
    # If "proxyConfig": "src/proxy.conf.json" had NOT been added to angular.json, then start server with:
    "start": "ng serve --proxy-config src/proxy.conf.json"
    Or
    ng serve --proxy-config src/proxy.conf.json

- In case if someone is looking for multiple context entries to the same target or TypeScript based configuration.

    # proxy.conf.ts:
    const proxyConfig = [
      {
        context: ['/api/v1', '/api/v2],
        target: 'https://example.com',
        secure: true,
        changeOrigin: true
      },
      {
        context: ['**'], // Rest of other API call
        target: 'http://somethingelse.com',
        secure: false,
        changeOrigin: true
      }
    ];
    module.exports = proxyConfig;

## Angular 19 special: components are standalone by default

If a component is not standalone and belongs to an NgModule, you should use standalone: false.

    ng update

## `npm un eslint-plugin-standard`

`eslint-plugin-standard@5.0.0`: `standard 16.0.0` and `eslint-config-standard 16.0.0` no longer require the `eslint-plugin-standard` package.
You can remove it from your dependencies with 'npm rm eslint-plugin-standard'

    npm un eslint-plugin-standard --force

## Replace `ts-node` with `tsx`

    npm un ts-node --force
    npm i -D tsx --force
