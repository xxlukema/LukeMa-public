# kubernetes deployment and service on docker desktop

[Deployments - Official]<https://kubernetes.io/docs/concepts/workloads/controllers/deployment/>

[Kubernetes Deployment Tutorial - yaml explained + Demo]<https://www.youtube.com/watch?v=y_vy9NVeCzo>

[Kubernetes YAML File Explained - Deployment and Service]<https://www.youtube.com/watch?v=qmDzcu5uY1I>

## Notes

1. `nginx-deployment-with-rolling.yml` is deployment for nginx with `selector label` of `enviroment:test`. It is only accessible to the `Cluster IP`
2. `nginx-service-nodeport.yml` exposes the above nginx deployment to outside world as a service. It `NodePort` is for test, not for production.
3. `nginx-deployment-no-rolling-as-reference.yml` is for reference. The only difference to `nginx-deployment-with-rolling.yml` is `selector label` is `app:nginx`
4. `nginx-service-loadbalancer.yml` is for reference. It serves as an example for `LoadBalancer` for production

## Steps

    ################################
    # 1. deploy nginx
    ################################
    kubectl apply -f nginx-deployment-with-rolling.yml
    # verify deployment
    kubectl get deployment -o wide
    > NAME               READY   UP-TO-DATE   AVAILABLE   AGE     CONTAINERS   IMAGES       SELECTOR
    > nginx-deployment   3/3     3            3           4m11s   nginx        nginx:1.23   environment=test
    #
    ################################
    # 2. deploy service
    ################################
    # use LoadBalancer (for production)
    kubectl apply -f nginx-service-loadbalancer.yml
    # or, use NodePort (for test)
    # kubectl apply -f nginx-service-nodeport.yml
    # verify service
    kubectl get svc -o wide
    > NAME                            TYPE           CLUSTER-IP      EXTERNAL-IP     PORT(S)          AGE     SELECTOR
    > kubernetes                      ClusterIP      10.96.0.1       <none>          443/TCP          22d     <none>
    > nginx-service                   LoadBalancer   10.103.204.94   192.168.1.201   8080:30413/TCP   5m35s   environment=test
    #
    ################################
    # 3. verify
    ################################
    kubectl get all
    > 
    # use `NodePort` or `Loadbalancer` `externalIPs` on either master node or Laptop gitbash
    curl http://192.168.1.201:8080/
    curl http://ubuntu-k8s-master:8080
    curl http://<external-ip>:<port>
    #
    ################################
    # 4. delete
    ################################
    kubectl delete -f nginx-deployment-with-rolling.yml
    # kubectl delete -f nginx-service-loadbalancer.yml
    # kubectl delete -f nginx-service-nodeport.yml
    #
    ################################
    # 5. troubleshoot deployment
    ################################
    kubectl get deployment
    # or
    kubectl get deployment nginx-deployment
    > NAME               READY   UP-TO-DATE   AVAILABLE   AGE
    > nginx-deployment   3/3     3            3           11m
    kubectl describe deployment nginx-deployment
    k get svc
    > NAME                            TYPE           CLUSTER-IP      EXTERNAL-IP     PORT(S)          AGE
    > kubernetes                      ClusterIP      10.96.0.1       <none>          443/TCP          22d
    > nginx-service                   LoadBalancer   10.103.204.94   192.168.1.201   8080:30413/TCP   14m
    #
    k get svc nginx-service
    > NAME            TYPE           CLUSTER-IP      EXTERNAL-IP     PORT(S)          AGE
    > nginx-service   LoadBalancer   10.103.204.94   192.168.1.201   8080:30413/TCP   15m
    #
    k get pods -o wide
    k get pods -l environment=test -o wide
    > NAME                               READY   STATUS    RESTARTS   AGE   IP              NODE                 NOMINATED NODE   READINESS GATES
    > nginx-deployment-7fd4fdbdc-rt6bz   1/1     Running   0          36m   172.16.251.67   ubuntu-k8s-worker1   <none>           <none>
    kubectl exec nginx-deployment-7cfdb99769-ngzf2 -- printenv | grep SERVICE
    > 
    curl http://172.16.251.67
    #
    ################################
    # 6. troubleshoot service
    ################################
    k get svc
    k describe svc nginx-service
    > Endpoints:                172.16.251.68:80,172.16.251.69:80,172.16.251.70:80
    # compare with ouput of
    k get pods -l environment=test -o wide

## Service

### 1. Deployment and Service are connected using `selector` value

The `selector` value of **Service** must match that of **Deployment**.

`.service.spec.selector` must match `.deployment.spec.selector.matchLabels`

### 2. Deployment and Service Ports

**Service** `targetPort` value must match that of **Deployment** `containerPort`

`.service.spec.ports.targetPort` must match `.deployment.spec.template.spec.containers.image.ports.containerPort`

    # use LoadBalancer (for production)
    kubectl apply -f nginx-service-loadbalancer.yml
    # or, use NodePort (for test)
    # kubectl apply -f nginx-service-nodeport.yml
    kubectl get svc -o wide
    kubectl get service -o wide

    # kubectl describe service [ServiceName]
    # Note: Service Endpoints and pods port match
    kubectl describe service nginx-service
    > Endpoints:                10.1.0.21:80,10.1.0.22:80,10.1.0.23:80
    kubectl get pods -o wide
    > NAME                               READY   STATUS    RESTARTS   AGE    IP          NODE             NOMINATED NODE   READINESS GATES
    > nginx-deployment-6f6b6cf67-2ht2m   1/1     Running   0          100m   10.1.0.23   docker-desktop   <none>           <none>
    > nginx-deployment-6f6b6cf67-rp459   1/1     Running   0          100m   10.1.0.22   docker-desktop   <none>           <none>
    > nginx-deployment-6f6b6cf67-wpgh6   1/1     Running   0          100m   10.1.0.21   docker-desktop   <none>           <none>

    kubectl get deployment nginx-deployment -o yaml

## Verify

    # not work
    # curl http://localhost:8080/
    # use NodePort externalIPs on either master node or Laptop gitbash
    curl http://192.168.1.201:8080/
    curl http://ubuntu-k8s-master:8080
    curl http://<external-ip>:<port>

## Delete

    kubectl delete -f nginx-deployment-with-rolling.yml
    # kubectl delete -f nginx-service-loadbalancer.yml
    # kubectl delete -f nginx-service-nodeport.yml

## All Commands

    # Requirements:
    # 1. WSL 2
    # 2. Ubuntu-22.04
    # 3. Docker integration with WSL
    # 4. Docker integration with Ubuntu-22.04
    # 5. Kubernetes enabled on Docker
    # Start WSL Ubuntu-22.04 Ternimal

    # alias k='kubectl'
    kubectl apply -f nginx-deployment-with-rolling.yml
    kubectl get all
    kubectl get pod
    kubectl get pods
    kubectl get pods -o wide
    kubectl get pods --show-labels

    kubectl get deployment nginx-deployment
    kubectl describe deployment nginx-deployment

    # delete a pod. another pod will be deployed automatically
    kubectl delete pod nginx-deployment-764d7b7f6-mlxpv
    kubectl get all
    
    # rs: ReplicaSet
    kubectl delete rs nginx-deployment-764d7b7f6
    kubectl get all
    kubectl get rs
    
    kubectl describe pods
    kubectl describe rs
    
    kubectl get deployment -o wide
    kubectl get deployment
    kubectl describe deployment

    # Update nginx version from 1.16 to 1.17, and run these commands:
    kubectl apply -f nginx-deployment-with-rolling.yml
    kubectl get all
    kubectl get rs --watch

    kubectl get pods
    kubectl describe pods
    
    # Now, it is possible to delete old ReplicaSet
    kubectl delete rs nginx-deployment-764d7b7f6
    kubectl get rs

    kubectl set image deployment.v1.apps/nginx-deployment nginx=nginx:1.23.3
    kubectl set image deployment/nginx-deployment nginx=nginx:1.23.3

    kubectl edit deployment/nginx-deployment

    kubectl rollout history deployment/nginx-deployment --revision=2
    kubectl rollout undo deployment/nginx-deployment
    kubectl rollout undo deployment/nginx-deployment --to-revision=2
    kubectl get deployment nginx-deployment
    kubectl describe deployment nginx-deployment

    kubectl scale deployment/nginx-deployment --replicas=10

    kubectl autoscale deployment/nginx-deployment --min=10 --max=15 --cpu-percent=80

    kubectl set image deployment/nginx-deployment nginx=nginx:sometag

    kubectl set resources deployment/nginx-deployment -c=nginx --limits=cpu=200m,memory=512Mi

    kubectl rollout pause deployment/nginx-deployment
    kubectl rollout resume deployment/nginx-deployment
    
    # delete
    kubectl delete -f nginx-deployment-with-rolling.yml
    # Or
    kubectl delete services nginx-service
    kubectl delete deployment nginx-deployment
    #
    # expose service
    # kubectl expose deployment/nginx-service
    # this is equavilent to
    # kubectl apply -f nginx-service-loadbalancer.yml
    # apiVersion: v1
    # kind: Service
    # metadata:
    #   name: nginx-service
    #   labels:
    #     run: nginx-service
    # spec:
    #   ports:
    #   - port: 80
    #     protocol: TCP
    #   selector:
    #     run: nginx-service
    #
    # this will delete all nginx-service pods and then create 2 replicas
    # kubectl scale deployment nginx-service --replicas=0; kubectl scale deployment nginx-service --replicas=2;
    # or
    # kubectl scale deployment nginx-service --replicas=0;
    # kubectl scale deployment nginx-service --replicas=2;

## DVS Sevice

    kubectl get services kube-dns --namespace=kube-system
    > NAME       TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)                  AGE
    > kube-dns   ClusterIP   10.96.0.10   <none>        53/UDP,53/TCP,9153/TCP   25d

## Reference: README.md

[Connect Applications with Service]<https://kubernetes.io/docs/tutorials/services/connect-applications-service/>

- [DNS]<https://kubernetes.io/docs/tutorials/services/connect-applications-service/#dns>
- [Securing the Service]<https://kubernetes.io/docs/tutorials/services/connect-applications-service/#securing-the-service>
- [Exposing the Service]<https://kubernetes.io/docs/tutorials/services/connect-applications-service/#exposing-the-service>
