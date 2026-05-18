# `my-properties-boot`

## Problems

1. `MetalLB` does not work on ubuntu 22.10. Therefore, unable to route traffic from `MetalLB` to Ingress
2. Unable to do port-forwarding for ingress. Perhaps this feature is not avaliable yet
3. Workaround is `kubectl port-forward service/my-properties-boot 8443:8443`. This will bypass Ingress, because Ingress does not use service.
4. Ingress bypasses service. (Ingress talks to pods/rs directly?)

## 1. deploy

    #
    # alias k='kubctl'
    ##################################
    # 1. deploy
    ##################################
    k apply -f my-properties-boot-deployment.yml
    k get all
    k describe deployment.apps/my-properties-boot
    k get endpoints
    k get pods
    k get pods -o wide
    curl -k "https://localhost:8443/my-properties-boot/rest/ping"
    > (no response)
    #
    ##################################
    # 2. expose
    ##################################
    k get all
    k apply -f my-properties-boot-service.yml
    k get all
    k get service/my-properties-boot -o=yaml
    curl -k "https://localhost:8443/my-properties-boot/rest/ping"
    (no response)
    #
    ##################################
    # 3. endpoints
    ##################################
    k get ns
    k get namespace
    #
    ##################################
    # 4. `port-forward` (kubernetes port forwarding is a proxy)
    ##################################
    # ssh -L 192.168.1.10:8443:10.0.0.10:8443 root@10.0.0.10
    # kubectl port-forward TYPE/NAME [options] LOCAL_PORT:REMOTE_PORT
    kubectl port-forward service/my-properties-boot 8443:8443
    # to expose the Kubernetes API on a specific port
    kubectl proxy --port=8443
    #
    ##################################
    # 5. ingress
    ##################################
    k get ing
    k describe ingress my-properties-boot-ingress
    #
    ##################################
    # 6. verify
    ##################################
    # in one putty terminal
    # kubectl port-forward TYPE/NAME [options] LOCAL_PORT:REMOTE_PORT
    kubectl port-forward service/my-properties-boot 8443:8443
    # in aother putty terminal
    curl -k https://localhost:8443/my-properties-boot/rest/ping
    curl -k -H "Connection: keep-alive" https://localhost:8443/my-properties-boot/rest/ping
    curl -k -H "Connection: close" https://localhost:8443/my-properties-boot/rest/ping
    curl -k https://localhost:8443/my-properties-boot/rest/ping
    > ...
    > <p><em>Thank you for using nginx.</em></p>
    #
    ##################################
    # 7. verify individual pod/deployment/service/replica
    ##################################
    k get all
    > NAME                               READY   STATUS    RESTARTS   AGE
    > pod/my-properties-boot-5bcd88dff8-crkxd   1/1     Running   0          15m
    > pod/my-properties-boot-5bcd88dff8-f4fd8   1/1     Running   0          15m
    > NAME                  TYPE        CLUSTER-IP       EXTERNAL-IP     PORT(S)          AGE
    > service/my-properties-boot   NodePort    10.111.255.147   192.168.1.201   8080:30855/TCP   15m
    > NAME                          READY   UP-TO-DATE   AVAILABLE   AGE
    > deployment.apps/my-properties-boot   2/2     2            2           15m
    > NAME                                     DESIRED   CURRENT   READY   AGE
    > replicaset.apps/my-properties-boot-5bcd88dff8   2         2         2       15m
    #
    # (all the command should be successful)
    k exec pod/my-properties-boot-5bcd88dff8-crkxd -- curl -k https://localhost:8443/my-properties-boot/rest/ping
    k exec my-properties-boot-5bcd88dff8-crkxd -- curl -k https://localhost:8443/my-properties-boot/rest/ping
    k exec my-properties-boot -- curl -k https://localhost:8443/my-properties-boot/rest/ping
    k exec deployment.apps/my-properties-boot -- curl -k https://localhost:8443/my-properties-boot/rest/ping
    k exec deployment.apps/my-properties-boot -- curl -k https://localhost:8443/my-properties-boot/rest/ping
    k exec service/my-properties-boot -- curl -k https://localhost:8443/my-properties-boot/rest/ping
    k exec replicaset.apps/my-properties-boot-5bcd88dff8 -- curl -k https://localhost:8443/my-properties-boot/rest/ping
    #
    k exec replicaset.apps/my-properties-boot-5bcd88dff8 -- curl -k https://localhost:8443/my-properties-boot/rest/house/getDateUpdated
    > ...
    > <p><em>Thank you for using nginx.</em></p>
    #
    ##################################
    # 8. clean up
    ##################################
    k delete -f my-properties-boot-service.yml,my-properties-boot-deployment.yml
    k delete pod/my-properties-boot-58c7b8fd68-4shj9 --grace-period=0 --force
    #
    ##################################
    # 9. kubectl exec
    ##################################
    # Note: The double dash (--) separates the arguments you want to pass to the command from the kubectl arguments.
    # The short options -i and -t are the same as the long options --stdin and --tty
    # getting shell in a container:
    # kubectl exec -it pod/podname -- /bin/sh
    #
    k get all
    # alpine doesn not has bash. alpine has /bin/sh only
    # kubectl exec -i -t pod/my-properties-boot-58c7b8fd68-xbvlc -- /bin/sh
    # kubectl exec --stdin --tty pod/my-properties-boot-58c7b8fd68-xbvlc -- /bin/sh
    #
    kubectl exec -it pod/my-properties-boot-58c7b8fd68-xbvlc -- /bin/sh
    #
    k get pod
    k exec pod/my-properties-boot-58c7b8fd68-xbvlc -- ls /
    k exec pod/my-properties-boot-58c7b8fd68-xbvlc -- ps aux
    k exec pod/my-properties-boot-58c7b8fd68-xbvlc -- ls /home/lma/
    k exec pod/my-properties-boot-58c7b8fd68-xbvlc -- cat /home/lma/logs/my-properties-boot.log
    #
    k exec service/my-properties-boot -- curl -k https://localhost:8443/my-properties-boot/rest/ping

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
    kubectl port-forward service/my-properties-boot 8443:8443
    # test short
    # on master node
    curl -k https://localhost:8443/my-properties-boot/rest/ping
    > ...
    > <p><em>Thank you for using nginx.</em></p>

## Test

    curl -k -i -L -X GET https://localhost:8443/my-properties-boot
    # test short
    # on master node
    curl -k -i -X GET https://localhost:8443/my-properties-boot/rest/ping
    #
    # test long
    curl -k -i -X GET https://localhost:8443/my-properties-boot/rest/house/getDateUpdated
    curl -k -i -X GET https://localhost:8443/my-properties-boot/rest/house/getPropertyList
    #
    # from laptop
    curl -k -i -X GET https://ubuntu-k8s-master:8443/my-properties-boot/rest/house/getDateUpdated

## Swagger

    [OpenApi]<https://ubuntu-k8s-master:8443/my-properties-boot/swagger-ui.html>
    Or
    [OpenApi]<https://ubuntu-k8s-master:8443/my-properties-boot/swagger-ui/index.html>

## `namespace` and logging

    kubectl --namespace my-namespace get pods
    alias k="kubectl --namespace my-namespace"
    k get pods
    kubectl logs pod-name
    kubectl logs pod-name container-name
    kubectl logs -l my-label=my-value --all-containers
    kubectl logs pod-name --since=2h
    kubectl logs pod-name --tail=10
    kubectl logs job/my-job
    kubectl logs deployment/my-deployment

## taint

    kubectl taint nodes `kubectl get nodes -o name` key=value:NoSchedule

## Creating a **Headless** service

Setting the `clusterIP` field in a service spec to `None` makes the service **headless**, and Kubernetes will not assign it a cluster IP through
which clients could connect to the pods behind it.

We know how services can be used to provide a stable IP address allowing clients to connect to pods (or endpoints). Each connection to the service
is forwarded to one randomly selected backing pod.

If we tell Kubernetes we do not need a cluster IP for our service by setting the `clusterIP` field to `None` in the service specification, the DNS
server will return the pod IPs instead of the single service IP.

    apiVersion: v1
    kind: Service
    metadata:
      name: bogo-headless
    spec:
      clusterIP: None    # <= Don't forget!!
      ports:
      - port: 80
        targetPort: 8080
      selector:
        app: bogo

    # `dnsutils` has `dig` and `nslookup` binaries
    k run dnsutils --image=tutum/dnsutils -- sleep infinity
    k exec dnsutils -- nslookup bogo-headless
    k exec dnsutils -it -- /bin/sh

Regular services can be used to provide a stable IP address allowing clients to connect to pods (or endpoints). Each connection to the service is forwarded
to one randomly selected backing pod for **load balancing**

Headless services still provides load balancing across pods, but through the DNS round-robin mechanism instead of through the service proxy.
