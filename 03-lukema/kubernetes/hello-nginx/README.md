# `hello-nginx`

[nginx image tags]<https://hub.docker.com/_/nginx>

## Problems

1. `MetalLB` does not work on ubuntu 22.10. Therefore, unable to route traffic from `MetalLB` to Ingress
2. Unable to do port-forwarding for ingress. Perhaps this feature is not avaliable yet
3. Workaround is `kubectl port-forward service/hello-nginx 8080:8080`. This will bypass Ingress, because Ingress does not use service.
4. Ingress bypasses service. (Ingress talks to pods/rs directly?)

## 1. deploy

    #
    # alias k='kubctl'
    ##################################
    # 1. deploy
    ##################################
    # k apply -f hello-nginx-deployment.yml -f hello-nginx-service.yml
    # or
    # k apply -f hello-nginx-deployment.yml,hello-nginx-service.yml
    # or
    k apply -f hello-nginx-deployment.yml
    k get all
    k describe deployment.apps/hello-nginx
    k describe replicaset.apps/hello-nginx-7f975bfb59
    k get endpoints
    k get pods
    k get pods -o wide
    > NAME                           READY   STATUS    RESTARTS   AGE    IP              NODE                 NOMINATED NODE   READINESS GATES
    > hello-nginx-7f975bfb59-r4snv   1/1     Running   0          8m9s   172.16.251.72   ubuntu-k8s-worker1   <none>           <none>
    > hello-nginx-7f975bfb59-x26dn   1/1     Running   0          8m9s   172.16.251.71   ubuntu-k8s-worker1   <none>           <none>
    curl "http://172.16.251.72"
    > (no response)
    #
    ##################################
    # 2. expose
    ##################################
    k get all
    k expose deployment.apps/hello-nginx
    # or
    k apply -f hello-nginx-service.yml
    k get all
    > NAME                  TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)   AGE
    > service/hello-nginx   ClusterIP   10.97.215.249   <none>        80/TCP    5m35s
    curl "http://10.97.215.249"
    (no response)
    #
    ##################################
    # 3. `port-forward` (kubernetes port forwarding is a proxy)
    ##################################
    # ssh -L 192.168.1.10:8080:10.0.0.10:80 root@10.0.0.10
    # kubectl port-forward TYPE/NAME [options] LOCAL_PORT:REMOTE_PORT
    # kubectl port-forward pods/redis-master-765d459796-258hz 6379:6379
    # kubectl port-forward deployment/redis-master 6379:6379 
    # kubectl port-forward rs/redis-master 6379:6379
    # kubectl port-forward svc/redis-master 6379:6379
    # kubectl port-forward --address localhost,10.19.21.23,0.0.0.0 pod/mypod 8888:5000
    # kubectl port-forward --address localhost,0.0.0.0 service/hello-nginx 8888:8080
    kubectl port-forward service/hello-nginx 8080:8080
    # to expose the Kubernetes API on a specific port
    kubectl proxy --port=8082
    #
    ##################################
    # 4. ingress
    ##################################
    k get ing
    k describe ingress hello-nginx-ingress
    #
    ##################################
    # 5. verify
    ##################################
    # in one putty terminal
    # kubectl port-forward TYPE/NAME [options] LOCAL_PORT:REMOTE_PORT
    kubectl port-forward service/hello-nginx 8080:80
    # in aother putty terminal
    curl http://localhost:8080
    > ...
    > <p><em>Thank you for using nginx.</em></p>
    #
    ##################################
    # 6. verify individual pod/deployment/service/replica
    ##################################
    k get all
    > NAME                               READY   STATUS    RESTARTS   AGE
    > pod/hello-nginx-5bcd88dff8-crkxd   1/1     Running   0          15m
    > pod/hello-nginx-5bcd88dff8-f4fd8   1/1     Running   0          15m
    > NAME                  TYPE        CLUSTER-IP       EXTERNAL-IP     PORT(S)          AGE
    > service/hello-nginx   NodePort    10.111.255.147   192.168.1.201   8080:30855/TCP   15m
    > NAME                          READY   UP-TO-DATE   AVAILABLE   AGE
    > deployment.apps/hello-nginx   2/2     2            2           15m
    > NAME                                     DESIRED   CURRENT   READY   AGE
    > replicaset.apps/hello-nginx-5bcd88dff8   2         2         2       15m
    #
    # (all the command should be successful)
    k exec pod/hello-nginx-5bcd88dff8-crkxd -- curl http://localhost
    k exec hello-nginx-5bcd88dff8-crkxd -- curl http://localhost
    k exec hello-nginx -- curl http://localhost
    k exec deployment.apps/hello-nginx -- curl http://localhost
    k exec deployment.apps/hello-nginx -- curl http://localhost
    k exec service/hello-nginx -- curl http://localhost
    k exec replicaset.apps/hello-nginx-5bcd88dff8 -- curl http://localhost
    > ...
    > <p><em>Thank you for using nginx.</em></p>
    #
    ##################################
    # 7. clean up
    ##################################
    k delete -f hello-nginx-service.yml -f hello-nginx-deployment.yml
    # or
    k delete -f hello-nginx-service.yml,hello-nginx-deployment.yml

## 2. `port-forward`

### 2.1 `kubectl port-forward`

    # in one putty terminal
    ##################################
    # 3. `port-forward` (kubernetes port forwarding is a proxy)
    ##################################
    # kubectl port-forward TYPE/NAME [options] LOCAL_PORT:REMOTE_PORT
    # kubectl port-forward pods/redis-master-765d459796-258hz 6379:6379
    # kubectl port-forward deployment/redis-master 6379:6379 
    # kubectl port-forward rs/redis-master 6379:6379
    # kubectl port-forward svc/redis-master 6379:6379
    # kubectl port-forward --address localhost,10.19.21.23,0.0.0.0 pod/mypod 8888:5000
    kubectl port-forward service/hello-nginx 8080:80
    # in aother putty terminal
    curl http://localhost:8080
    > ...
    > <p><em>Thank you for using nginx.</em></p>

### 2.2 `docker run -p 8080:80 nginx:latest`

    curl http://localhost:8080
    > ...
    > <p><em>Thank you for using nginx.</em></p>

### 2.3 `docker-compose up`

    # docker-compose.yml:
    version: '3.8'
    services:
      nginx:
        image: nginx:latest
        ports:
          - 8080:80

    curl http://localhost:8080
    > ...
    > <p><em>Thank you for using nginx.</em></p>

## Generate `service.yaml`

    # k get service/hello-nginx -o=yaml > hello-nginx-service.yml
    apiVersion: v1
    kind: Service
    metadata:
      creationTimestamp: "2023-01-07T23:17:35Z"
      name: hello-nginx
      namespace: default
      resourceVersion: "1021971"
      uid: db552f68-21ed-4faa-a149-319da2066652
    spec:
      clusterIP: 10.97.215.249
      clusterIPs:
      - 10.97.215.249
      internalTrafficPolicy: Cluster
      ipFamilies:
      - IPv4
      ipFamilyPolicy: SingleStack
      ports:
      - port: 80
        protocol: TCP
        targetPort: 80
      selector:
        app: nginx
      sessionAffinity: None
      type: ClusterIP
    status:
      loadBalancer: {}

## `MetalLB`

[MetalLB]<https://metallb.universe.tf/>

## Namespace

    k get ns
    k get namespace

    # To set the namespace for a current request, use the --namespace flag
    kubectl run nginx --image=nginx --namespace=<insert-namespace-name-here>
    kubectl get pods --namespace=<insert-namespace-name-here>
    # to permanently save the namespace for all subsequent kubectl commands
    kubectl config set-context --current --namespace=<insert-namespace-name-here>
    # Validate it
    kubectl config view --minify | grep namespace

**Notes**:

1. When you create a Service, it creates a corresponding DNS entry. This entry is of the form `<service-name>.<namespace-name>.svc.cluster.local`,
   which means that if a container only uses `<service-name>`, it will resolve to the service which is local to a namespace.
2. If you want to reach across namespaces, you need to use the fully qualified domain name (FQDN).

## `NetworkPolicy`

[Network Policy]<https://kubernetes.io/docs/concepts/services-networking/network-policies/>

### Default policies

By default, if no policies exist in a namespace, then all ingress and egress traffic is allowed to and from pods in that namespace.

    kubectl get all --namespace=kube-system
    kubectl get pods --namespace=kube-system
    kubectl get NetworkPolicy --namespace=kube-system
    kubectl get networkpolicy --namespace=kube-system
    kubectl describe networkpolicy <networkpolicy-name>

### Change Default Network Policy

    #######################################
    # By default, if no policies exist in a namespace, then all ingress and egress traffic is allowed to and from pods in that namespace.
    #######################################
    # deny all ingress traffic
    ---
    apiVersion: networking.k8s.io/v1
    kind: NetworkPolicy
    metadata:
      name: default-deny-ingress
    spec:
      podSelector: {}
      policyTypes:
      - Ingress
  
    # allow all ingress traffic
    ---
    apiVersion: networking.k8s.io/v1
    kind: NetworkPolicy
    metadata:
      name: allow-all-ingress
    spec:
      podSelector: {}
      ingress:
      - {}
      policyTypes:
      - Ingress
    
    # deny all egress traffic
    ---
    apiVersion: networking.k8s.io/v1
    kind: NetworkPolicy
    metadata:
      name: default-deny-egress
    spec:
      podSelector: {}
      policyTypes:
      - Egress
    
    # allow all egress traffic
    ---
    apiVersion: networking.k8s.io/v1
    kind: NetworkPolicy
    metadata:
      name: allow-all-egress
    spec:
      podSelector: {}
      egress:
      - {}
      policyTypes:
      - Egress
    
    # deny all ingress and all egress traffic
    ---
    apiVersion: networking.k8s.io/v1
    kind: NetworkPolicy
    metadata:
      name: default-deny-all
    spec:
      podSelector: {}
      policyTypes:
      - Ingress
      - Egress

## Test

    # the following commands do not work!
    # sudo iptables-legacy -t nat -A PREROUTING -p tcp --dport 8080 -j DNAT --to-destination 192.168.1.240:8080
    # iptables -t nat -A POSTROUTING ! -s localhost -j MASQUERADE
    # iptables -t nat -A POSTROUTING ! -s 127.0.0.1 -j MASQUERADE
