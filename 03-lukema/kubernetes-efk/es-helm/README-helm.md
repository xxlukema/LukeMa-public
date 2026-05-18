# `helm`

## Commands

    # 1. add elastic to repo
    helm repo add elastic https://helm.elastic.co
    helm repo update

    # 2. install elastic
    helm install elastic-master elastic/elasticsearch -f ./master.yml
    > no persistent volumes available for this claim and no storage class is set

    # 3. clean up
    helm uninstall elastic-master
    helm list
    k get all
    k get pvc
    k delete pvc --all
    k delete pv --all

    # 4.


    helm install elastic-master elastic/elasticsearch -f ./master.yml
    helm install elastic-data elastic/elasticsearch -f ./data.yml
    helm install elastic-client elastic/elasticsearch -f ./client.yml

    helm list
    > NAME                            NAMESPACE       REVISION
    > elastic-master      default         1

    helm search elastic-master
    
    helm repo update
    
    helm install [NAME] [CHART] [flags]
    helm install bitnami/mysql --generate-name

    helm show all bitnami/mysql

    helm uninstall mysql-1612624192
    helm status mysql-1612624192

    helm search
    helm search hub
    helm search hub elastic
    helm serach repo
    helm serach repo elastic

    helm status
    helm status elastic

    helm list
    > elastic-master
    helm status elastic-master
    helm uninstall elastic-master

    echo '{mariadb.auth.database: user0db, mariadb.auth.username: user0}' > values.yml
    helm install -f values.yml bitnami/wordpress --generate-name

    helm install -f myvalues.yml myredis ./redis
    helm install --set name=prod myredis ./redis
    helm install --set-string long_int=1234567890 myredis ./redis
    helm install --set-file my_script=dothings.sh myredis ./redis
    helm install --set-json 'master.sidecars=[{"name":"sidecar","image":"myImage","imagePullPolicy":"Always","ports":[{"name":"portname","containerPort":1234}]}]' myredis ./redis
    helm install -f myvalues.yml -f override.yml  myredis ./redis
    helm install --set foo=bar --set foo=newbar  myredis ./redis
    helm install --set-json='foo=["one", "two", "three"]' --set-json='foo=["four"]' myredis ./redis
    helm install --set-json='foo={"key1":"value1","key2":"value2"}' --set-json='foo.key2="bar"' myredis ./redis

    --set a=b,c=d
    --set outer.inner=value
    --set name={a, b, c}
    --set name=[],a=null
    --set servers[0].port=80
    --set servers[0].port=80,servers[0].host=example 
    --set name=value1\,value2
    --set nodeSelector."kubernetes\.io/role"=master

    helm install foo foo-0.1.1.tgz
    helm install foo path/to/foo
    helm install foo https://example.com/charts/foo-1.2.3.tgz

    helm upgrade -f panda.yml happy-panda bitnami/wordpress
    helm get values happy-panda
    helm rollback happy-panda 1

    helm uninstall happy-panda

    helm list --all
    helm list --uninstalled

    helm repo list
    helm repo add dev https://example.com/dev-charts
    repo update

    helm repo remove

    helm create deis-workflow
    helm package deis-workflow
    helm install deis-workflow ./deis-workflow-0.1.0.tgz
    helm install release1 angular-node-chart-0.1.0.tgz

    kubectl create deployment nginx --image=nginx --dry-run=client -o yaml > templates/deployment.yml
    kubectl expose deploy nginx --port 80 --type NodePort --dry-run=client -o yaml > templates/service.yml
    echo "This is first helm chart and it will deploy nginx application" >> templates/NOTES.txt
    helm lint ./deis-workflow
    helm install deis-workflow ./deis-workflow --dry-run 
    helm list

    helm search mysql cluster
    helm repo add bitnami https://charts.bitnami.com/bitnamihelm install my-release bitnami/mysql

    helm search hub -h
    helm search hub postgres -o yaml
    helm search hub --max-col-width 160
    helm search hub fluentd --list-repo-url

    helm pull

    # efk
    helm search hub elastic --list-repo-url --max-col-width 140 | grep fluent
    > https://artifacthub.io/packages/helm/btungut/fluentd-kube-elastic  1.15.4 1.15-1-rev1  https://btungut.github.io

    helm repo add btungut https://btungut.github.io
    helm repo update
    helm repo list
    helm pull btungut/fluentd-kube-elastic --untar

## `helm` Install

    # get latest tag number
    https://github.com/helm/helm/releases

    wget https://get.helm.sh/helm-v3.11.0-linux-amd64.tar.gz
    tar xvf helm-v3.11.0-linux-amd64.tar.gz
    sudo mv linux-amd64/helm /usr/local/bin
    rm -rf helm-v3.11.0-linux-amd64.tar.gz
    rm -rf linux-amd64/

    # Or
    curl https://baltocdn.com/helm/signing.asc | sudo apt-key add -
    sudo apt-get install apt-transport-https --yes
    echo "deb https://baltocdn.com/helm/stable/debian/ all main" | sudo tee /etc/apt/sources.list.d/helm-stable-debian.list
    sudo apt-get update
    sudo apt-get install helm

## Chart Files Structure

    wordpress/
      Chart.yml          # A yml file containing information about the chart
      LICENSE             # OPTIONAL: A plain text file containing the license for the chart
      README.md           # OPTIONAL: A human-readable README file
      values.yml         # The default configuration values for this chart
      values.schema.json  # OPTIONAL: A JSON Schema for imposing a structure on the values.yml file
      charts/             # A directory containing any charts upon which this chart depends.
      crds/               # Custom Resource Definitions
      templates/          # A directory of templates that, when combined with values,
                          # will generate valid Kubernetes manifest files.
      templates/NOTES.txt # OPTIONAL: A plain text file containing short usage notes

## `MetalLB`

    kubectl create -f metallb-config.yml
    kubectl create deploy nginx --image=nginx
    kubectl expose deploy nginx --port 80 --type LoadBalancer
