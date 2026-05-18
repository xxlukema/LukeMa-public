# MetalLB

- [MetalLB]<ttps://metallb.universe.tf/>
- [Installation By Manifest]<https://metallb.universe.tf/installation/#installation-by-manifest>
- [Configration]<https://metallb.universe.tf/configuration/>

## Install

    MetalLB_RTAG=$(curl -s https://api.github.com/repos/metallb/metallb/releases/latest | grep tag_name | cut -d '"' -f 4 | sed 's/v//')
    echo $MetalLB_RTAG
    # kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v${MetalLB_RTAG}/config/manifests/metallb-native.yaml
    wget https://raw.githubusercontent.com/metallb/metallb/v$MetalLB_RTAG/config/manifests/metallb-native.yaml
    kubectl apply -f metallb-native.yaml
    > ...
    > validatingwebhookconfiguration.admissionregistration.k8s.io/metallb-webhook-configuration configured
    k get ns
    kubectl get all -n metallb-system
    kubectl get pods -n metallb-system
    kubectl get endpoints -n metallb-system
    kubectl get service -n metallb-system webhook-service
    k describe service/webhook-service -n metallb-system
    # !important: in '{.data.ca\.crt}' the backslash is required
    # kubectl -n metallb-system get secret webhook-server-cert -ojsonpath='{.data.ca\.crt}' | base64 -d > caBundle.pem
    # curl --cacert ./caBundle.pem --resolve webhook-service.metallb-system.svc:443:10.96.50.216 https://webhook-service.metallb-system.svc:443/validate-metallb-io-v1beta1-ipaddresspool

## L2 Configuration

    # check ubuntu version
    lsb_release -a
    > Description:    Ubuntu 22.10
    #
    kubectl get deploy controller -n metallb-system -o wide
    # 
    # L2 Configuration
    # first-pool-l2-config.yml:
    apiVersion: metallb.io/v1beta1
    kind: IPAddressPool
    metadata:
      name: first-pool
      namespace: metallb-system
    spec:
      addresses:
      - 192.168.1.240-192.168.1.250

    # create first-pool-l2-config.yml
    k apply -f first-pool-l2-config.yml
    > Error from server (InternalError): error when creating "first-pool-l2-config.yml": Internal error occurred: failed calling webhook
    > "ipaddresspoolvalidationwebhook.metallb.io": failed to call webhook:
    > Post "https://webhook-service.metallb-system.svc:443/validate-metallb-io-v1beta1-ipaddresspool?timeout=10s": context deadline exceeded
    kubectl get -A IPAddressPool
    k get ipaddresspools.metallb.io -A
    k describe ipaddresspools.metallb.io first-pool -n metallb-system
    kubectl api-resources  | grep -E -i "(IPAddressPool|L2Advertisement)"

    #
    # advertisement.yml
    apiVersion: metallb.io/v1beta1
    kind: L2Advertisement
    metadata:
      name: example
      namespace: metallb-system
    spec:
      ipAddressPools:
      - first-pool

    #
    k apply -f advertisement.yml
    kubectl get -A L2Advertisement
    k get l2advertisement.metallb.io -A
    k describe l2advertisement.metallb.io example -n metallb-system
    k -n metallb-system get all
    #
    k create deploy nginx --image nginx
    k expose deploy nginx --port 80 --type Loadbalancer
    k get all
    #
    # verify
    curl http://192.168.3.201
    #
    # crd: customer resource definition
    k get crds | grep metallb
    kubectl get crd
    #
    kubectl get svc -n metallb-system
