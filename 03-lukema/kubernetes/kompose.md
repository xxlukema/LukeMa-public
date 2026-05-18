# Kompose

[Kompose Releases]<https://github.com/kubernetes/kompose/releases>

## Install

    # Linux
    curl -L https://github.com/kubernetes/kompose/releases/download/v1.27.0/kompose-linux-amd64 -o kompose
    
    # macOS
    curl -L https://github.com/kubernetes/kompose/releases/download/v1.27.0/kompose-darwin-amd64 -o kompose
    
    chmod +x kompose
    sudo mv ./kompose /usr/local/bin/kompose

## Use

    # (1) there must be a `docker-compose.yml` file
    # (2) `docker-compose.yml` file must have `version: '3.?'`
    kompose convert
    > INFO Kubernetes file "client-service.yaml" created 
    > INFO Kubernetes file "client-pod.yaml" created 
    > INFO Kubernetes file "backend-service.yaml" created 
    > INFO Kubernetes file "backend-pod.yaml" created 
    # (separated by comma ','. NOT space ' ')
    kubectl apply -f client-service.yaml,client-pod.yaml,backend-service.yaml,backend-pod.yaml

## Test

    kubectl apply -f my-properties-boot-logger-lma-service.yaml,my-properties-boot-logger-lma-pod.yaml,my-properties-boot-logger-lma-claim0-persistentvolumeclaim.yaml
    kubectl get all
    > NAME                                    TYPE           CLUSTER-IP       EXTERNAL-IP     PORT(S)          AGE
    > service/my-properties-boot-logger-lma   ClusterIP      10.103.190.103   <none>          8443/TCP         5m22s
    kubectl describe svc my-properties-boot-logger-lma
