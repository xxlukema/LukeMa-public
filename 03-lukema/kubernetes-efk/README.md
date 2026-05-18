# kubernetes-efk

EFK Kubernetes manifests

## Start

    # 1. 
    kubectl apply -f elasticsearch/es-svc.yaml

    # 2. es statefulSet
    kubectl apply -f elasticsearch/es-sts.yaml

## list `pv`, `pvc`

    k get pvc
    k get pv

## delete `pv`, `pvc`

    k delete pvc elasticsearch-data-es-cluster-0

    kubectl delete pvc --all
    kubectl delete pv --all
    kubectl delete pod --all

    kubectl delete pvc pvc-name
    kubectl delete pv pv-name
    kubectl delete pod pod-name

## Clean up

    # 2. es statefulSet
    kubectl delete -f elasticsearch/es-sts.yaml

    # 1. 
    kubectl delete -f elasticsearch/es-svc.yaml

## `helm`

    helm repo add elastic https://helm.elastic.co
    helm install elasticsearch-multi-master elastic/elasticsearch -f ./master.yaml
    helm install elasticsearch-multi-data elastic/elasticsearch -f ./data.yaml
    helm install elasticsearch-multi-client elastic/elasticsearch -f ./client.yaml

## `helm` Install

    wget https://get.helm.sh/helm-v3.11.0-linux-amd64.tar.gz
    tar xvf helm-v3.11.0-linux-amd64.tar.gz
    sudo mv linux-amd64/helm /usr/local/bin
    rm -rf helm-v3.11.0-linux-amd64.tar.gz
    rm -rf linux-amd64/
