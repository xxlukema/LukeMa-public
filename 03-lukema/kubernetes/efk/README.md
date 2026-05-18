# kubernetes-efk

[EFK Kubernetes]<https://devopscube.com/setup-efk-stack-on-kubernetes/>

1. `Elasticsearch` is deployed as a `Statefulset` and the multiple replicas connect with each other using a **headless service**
2. The **headless** svc helps in the **DNS** domain of the pods.
3. If you do not need load-balancing and a single Service IP, you can create what are termed "headless" Services.
4. Headless service: `clusterIP: None #### <== Headless` (.spec.clusterIP).

## Step 1 — Creating Namespace

1. Namespaces: groups of resources within a single cluster.
2. Names of resources need to be unique within a namespace, but not across namespaces.
3. Applicable only for namespaced objects (e.g. Deployments, Services, etc). Not for cluster-wide objects (e.g. StorageClass, Nodes, PersistentVolumes, etc)
4. When you create a `Service`, it creates a corresponding DNS entry of `<service-name>.<namespace-name>.svc.cluster.local`

### Namespace commands

    k get ns
    k get namespace
    > NAME              STATUS   AGE
    > default           Active   1d
    > kube-node-lease   Active   1d
    > kube-public       Active   1d
    > kube-system       Active   1d

    kubectl get all --namespace=efk-logging --show-labels
    #
    # `--namespace` can be shortened to `-n`
    kubectl get all -n=efk-logging --show-labels

    # 1. Create namespace
    k apply -f namespace.yml

    # ConfigMap
    k get configmap -n efk-logging
    k describe configmap efk-configmap -n efk-logging

    kubectl run nginx --image=nginx --namespace=<insert-namespace-name-here>
    kubectl get pods --namespace=<insert-namespace-name-here>

    kubectl config set-context --current --namespace=<insert-namespace-name-here>
    # Validate it
    kubectl config view --minify | grep namespace

    # Not all resources are namespaced.
    # In a namespace (`services`, `replication controllers`, etc)
    kubectl api-resources --namespaced=true
    # Not in a namespace (And low-level resources, such as `namespace`, `nodes`, and `persistentVolumes`, etc)
    kubectl api-resources --namespaced=false

    # set active namespace to `test`
    sudo snap install kubectx
    kubectx
    kubens test
    kubens -

### Labels and Selectors

1. Labels allow for efficient "queries and watches" and are ideal for use in UIs and CLIs.
2. Non-identifying information should be recorded using annotations.

### Commands

    kubectl explain <Resource Name>
    kubectl api-resources -o wide
    kubectl api-versions
    kubectl explain deployment
    kubectl explain deploy
    kubectl explain deploy --api-version apps/v1


    # kubectl apply $(ls *.yaml | awk ' { print " -f " $1 } ')
    # kubectl apply -f <folder>
    # kubectl apply -f .

    # 2. Deploy Elasticsearch Statefulset
    k apply -f elasticsearch/elasticsearch-statefulset.yml

## Step 2 — Create Elasticsearch `StatefulSet`

    k get all --namespace=efk-logging
    > NAME                              READY   STATUS    RESTARTS   AGE
    > pod/elasticsearch-statefulset-0   0/1     Pending   0          7s
    #
    k describe pod/elasticsearch-statefulset-0 --namespace=efk-logging
    > Warning  FailedScheduling  0/2 nodes are available: pod has unbound immediate PersistentVolumeClaims.

<https://www.digitalocean.com/community/tutorials/how-to-set-up-an-elasticsearch-fluentd-and-kibana-efk-logging-stack-on-kubernetes>

### Step 2.1 Creating Headless Service

    k apply -f elasticsearch-svc.yml
    kubectl get services --namespace=efk-logging
    kubectl get all --namespace=efk-logging --show-labels

## Step 3 — Create Kibana Deployment and Service

    k apply -f kibana-deployment-service.yml
    kubectl port-forward kibana-9cfcnhb7-lghs2 5601:5601 — namespace=efk-logging
    curl http://localhost:5601

## Step 4 — Create `Fluentd/FluentdBit` `DaemonSet` in the cluster

## Test

    http://localhost:5601

## Pull an image from Docker Hub

1. To download a particular image, or set of images (i.e., a repository), use `docker image pull ...` (or the `docker pull` shorthand).
2. If no tag is provided, Docker Engine uses the :latest tag as a default. This example pulls the debian:latest image:

## `docker image pull ...`

    docker image pull debian
    # docker pull ubuntu:22.04

## Labels, Selector

    apiVersion: apps/v1
    kind: Deployment
    metadata:
      name: kibana-deployment   # 0. <== display name of `kubectl get all`
      namespace: efk-logging
      labels:
        app: kibana-deployment  # 1. <== Name of deployed objects. For `kubectl delete/get <name>`, `kubectl delete/get deployment -l app=kibana-deployment`
        environment: demo
    spec:
      replicas: 1
      selector:
        matchLabels:
          app: kibana-pod  # 2. <== Looking for pods with this label "app=kibana-pod". If no matching pods when the deployment is first created,
                           #        the deployment will do nothing. Otherwise, the deployment will manage those pods. (must match with #3)
      template:
        metadata:
          labels:
            app: kibana-pod  # 3. <== Label to actual pod. (must match with #2)
        spec:
          containers:
            - name: kibana
              image: docker.elastic.co/kibana/kibana:7.5.0
              resources:
                limits:
                  memory: 512Mi
                  cpu: "1"
                requests:
                  memory: 256Mi
                  cpu: "0.2"
              env:
                - name: ELASTICSEARCH_URL
                  value: http://elasticsearch:9200
              ports:
                - containerPort: 5601
