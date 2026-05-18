# elastic quickstart

"https://www.elastic.co/guide/en/cloud-on-k8s/master/k8s-orchestration.html"

## Commands

    # 1. create crds/opeator
    kubectl create -f https://download.elastic.co/downloads/eck/2.6.1/crds.yaml
    kubectl apply -f https://download.elastic.co/downloads/eck/2.6.1/operator.yaml

    # 2. create nodeSet
    k apply -f node-set.yml

    k get elasticsearch
    > Pending...

    # clean up
    k delete -f crds.yaml,operator.yaml

    # skip following
    kubectl get pods --selector='elasticsearch.k8s.elastic.co/cluster-name=quickstart'
    kubectl logs -f quickstart-es-default-0
    kubectl get service quickstart-es-http
    
    PASSWORD=$(kubectl get secret quickstart-es-elastic-user -o go-template='{{.data.elastic | base64decode}}')

    curl -u "elastic:$PASSWORD" -k "https://quickstart-es-http:9200"

    kubectl port-forward service/quickstart-es-http 9200

    curl -u "elastic:$PASSWORD" -k "https://localhost:9200"
