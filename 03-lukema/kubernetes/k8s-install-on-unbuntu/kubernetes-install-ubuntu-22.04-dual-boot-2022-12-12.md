
# Kubernetes k8s

    # display service status
    service --status-all

    journalctl -f -u kubelet

## Update Ubuntu

    # current ubuntu version
    lsb_release -a
    > Description:    Ubuntu 22.04 LTS
    # Run this inside Ubuntu-22.04 trminal
    apt list --upgradable
    sudo apt upgrade -y && sudo apt full-upgrade -y && sudo apt autoremove -y
    sudo apt-get update --fix-missing

## Setup a "Docker-less" Multi-node Kubernetes Cluster On Ubuntu Server

[How to Install Kubernetes Cluster on Ubuntu 22.04]<https://www.linuxtechi.com/install-kubernetes-on-ubuntu-22-04/>

[Tutorial](https://www.youtube.com/watch?v=NsDhBEsTTHs)

[Setup a "Docker-less" Multi-node Kubernetes Cluster On Ubuntu Server]<https://www.youtube.com/watch?v=H9YfKliGuUY>

- Memory: 2 GiB or more of RAM per machine (master and workers)
- CPUs: At least 2 CPUs on the control plane machine (master)
- AWS Instance: t2.medium, 2 CPU, 4GB Memory

<pre>
# k8s master requires 2 CPUs and 2GiB memory minimum.
cat /proc/meminfo
# Or
free -m
#
nproc
sudo apt update -y && sudo apt full-upgrade -y && sudo apt autoremove -y
# Or
sudo apt update -y && sudo apt upgrade -y && sudo apt autoremove -y
</pre>

- Minimal install Ubuntu 22.04
- Minimum 2GB RAM or more
- Minimum 2 CPU cores / or 2 vCPU
- 20 GB free disk space on /var or more
- Sudo user with admin rights
- Internet connectivity on each node

Lab Setup

- Master Node:  192.168.1.201 – ubuntu-k8s-master
- First Worker Node:  192.168.1.202 – ubuntu-k8s-worker1

Add the following entries in `/etc/hosts` file on each node

    127.0.0.1         ubuntu-k8s-master
    # 127.0.0.1       localhost
    192.168.1.201     ubuntu-k8s-master
    192.168.1.202     ubuntu-k8s-worker1

## For 'root' user, add

    chmod a+r /etc/kubernetes/admin.conf
    export KUBECONFIG=/etc/kubernetes/admin.conf

## Install kubeadm --- Run the following commands as root

## Install `kubeadm`

Prepare for ubuntu

    sudo su
    cp /home/lma/.bashrc .

    apt list --upgradable
    
    apt update -y && apt upgrade -y && apt autoremove -y
    # ifconfig is inside of net-tools
    apt install -y net-tools sudo dos2unix
    ifconfig -a

## 1. Create master/control-plane and worker nodes

- ubuntu-k8s-master: 192.168.1.201
- ubuntu-k8s-worker1: 192.168.1.202

## Required ports

**Note**: Port `6443` is required for control plane

    # This command should return immediately
    nc 127.0.0.1 6443
    nc ubuntu-k8s-master 6443

## 2. On both **master** and **woker**, do all the following steps

[Install kubadmin](https://www.youtube.com/watch?v=l7gC4SgW7DU)

    # 1. Give each node a unique name
    # Run this on master-node (Already done through ubuntu `Wi-Fi` config)
    hostnamectl set-hostname ubuntu-k8s-master
    # Run this on worker-node1 (Already done through ubuntu `Wi-Fi` config)
    hostnamectl set-hostname ubuntu-k8s-worker1

    # 2. Enable transparent masquerading/bridged traffic
    # Load overlay and netfilter modules
    lsmod | grep br_netfilter   # If br_netfilter is not loaded, load it with the following command

    cat <<EOF | sudo tee /etc/modules-load.d/k8s.conf
    overlay
    br_netfilter
    EOF
    
    modprobe overlay
    modprobe br_netfilter
    #
    # Then run lsmod again
    lsmod | grep br_netfilter
    
    # Allow packets arriving at the node's network interface to be forwarded to pods
    echo '1' > /proc/sys/net/ipv4/ip_forward

    # 3. Update iptable
    # sysctl params required by setup, params persist across reboots
    cat <<EOF | sudo tee /etc/sysctl.d/k8s.conf
    net.bridge.bridge-nf-call-iptables  = 1
    net.bridge.bridge-nf-call-ip6tables = 1
    net.ipv4.ip_forward                 = 1
    EOF

    #
    # verify
    sysctl net.bridge.bridge-nf-call-iptables net.bridge.bridge-nf-call-ip6tables net.ipv4.ip_forward
    
    # 4. Disable SELinux
    apt install -y selinux-utils
    setenforce 0

    # 5. Disable SWAP
    # sed -i '/swap/d' /etc/fstab
    free -h
    # swapoff is volatile. 
    swapoff -a
    free -h
    
    # two steps to turn off swap permenantly:
    # step 1/2. remove swapfile
    sudo rm /swapfile

    #    
    # step 2/2. remove following line from /etc/fstab
    #
    vi /etc/fstab
    # comment out this line:
    # /swapfile       none    swap    sw      0       0

    # 6. apply the above settings
    # reload to apply above changes
    sysctl --system

    # 7. install containerd.io from docker
    #
    # apt install -y apt-transport-https curl
    sudo apt install -y curl gnupg gnupg2 software-properties-common apt-transport-https ca-certificates
    #
    # Install containerd.io
    # kuernetes talk to containerd, and containerd talks to docker. containerd is a standard interface between kubernetes and docker or other conainers.
    #
    # clean up apt-repository
    # sudo add-apt-repository --remove ppa:whatever/ppa
    # add-apt-repository --remove "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
    # ls -1 (number 1, not lowercase L)
    # ls -1 /etc/apt/sources.list.d/
    # > archive_uri-https_apt_kubernetes_io_-jammy.list
    # > archive_uri-https_download_docker_com_linux_ubuntu-jammy.list
    # sudo rm /etc/apt/sources.list.d/<Repo_Filename>.list
    # sudo rm /etc/apt/sources.list.d/archive_uri-https_apt_kubernetes_io_-jammy.list
    #
    # for wsl, run `wsl --update` from `PowerShell` as `administration` before install docker can solve many problems.
    #
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmour -o /etc/apt/trusted.gpg.d/docker.gpg
    sudo add-apt-repository -y "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"

    # there are two gpg.d folders: /etc/apt/trusted.gpg.d (for docker), and /etc/apt/sources.list.d/ (for kubernetes and ubuntu)
    ls -1 /etc/apt/trusted.gpg.d/
    ls -1 /etc/apt/sources.list.d/

    #
    sudo apt update -y && apt upgrade -y && sudo apt autoremove -y
    # sudo apt install -y --allow-change-held-packages docker-ce docker-ce-cli docker-compose-plugin && sudo apt autoremove -y
    # (run as lma) sudo usermod -aG docker $USER
    # sudo chmod a+rw /var/run/docker.sock
    sudo apt install -y --allow-change-held-packages containerd.io && sudo apt autoremove -y
    #
    # run purge containerd.io and docker-ce, if needed
    # sudo apt purge -y --allow-change-held-packages containerd.io && sudo apt autoremove -y
    # sudo apt purge -y --allow-change-held-packages docker-ce docker-ce-cli docker-compose-plugin && sudo apt autoremove -y
    #
    # run this as user `lma`
    (run as lma) sudo usermod -aG docker $USER

    # customerize `containerd`
    sudo mkdir -p /etc/containerd/
    containerd config default | tee /etc/containerd/config.toml > /dev/null 2>&1
    # change `SystemdCgroup = false` to `SystemdCgroup = true`
    sed -i 's/SystemdCgroup \= false/SystemdCgroup \= true/g' /etc/containerd/config.toml

    # (Optional) Install CNI Plugins For `containerd`
    # For the container to run, you need to install CNI plugins
    # sudo mkdir -p /opt/cni/bin/
    # cd /opt/cni/bin/
    # sudo wget https://github.com/containernetworking/plugins/releases/download/v1.1.1/cni-plugins-linux-amd64-v1.1.1.tgz
    # sudo tar Cxzvf /opt/cni/bin cni-plugins-linux-amd64-v1.1.1.tgz

    # prefer `systemctl enable containerd` to `systemctl enable containerd.service`:
    # For most service management commands, you can actually leave off the .service suffix
    # sudo systemctl enable containerd.service
    #
    systemctl restart containerd
    systemctl enable containerd

    apt-mark hold containerd
    # apt-mark hold docker-ce docker-ce-cli docker-compose-plugin

    # 8. install kubernetes (kubelet kubeadm kubectl)
    #
    # Port `6443` is required for control plane
    # This command should return immediately
    nc 127.0.0.1 6443
    nc ubuntu-k8s-master 6443

    [Install using native package management]<https://kubernetes.io/docs/tasks/tools/install-kubectl-linux/#install-using-native-package-management>
    #
    # add Google cloud apt-key for k8s
    sudo curl -fsSLo /etc/apt/keyrings/kubernetes-archive-keyring.gpg https://packages.cloud.google.com/apt/doc/apt-key.gpg

    # add k8s repo to yum repo
    # ubuntu xenial - 2020-08-18 17:01 - Ubuntu 16.04.7 LTS (Xenial Xerus)
    # ubuntu jammy - 2022-08-11 11:16 - Ubuntu 22.04.1 LTS (Jammy Jellyfish)
    # ubuntu kinetic - 2022-10-20 17:11 - Ubuntu 22.10 (Kinetic Kudu)
    sudo echo "deb [signed-by=/etc/apt/keyrings/kubernetes-archive-keyring.gpg] https://apt.kubernetes.io/ kubernetes-xenial main" | sudo tee /etc/apt/sources.list.d/kubernetes.list
    #
    # $(lsb_release -cs) displays codename in short (example: kinetic, jammy, xenial). $(lsb_release -a) displays everything
    # sudo echo "deb [signed-by=/etc/apt/keyrings/kubernetes-archive-keyring.gpg] https://apt.kubernetes.io/ kubernetes-$(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/kubernetes.list

    ls -1 /etc/apt/sources.list.d/

    sudo apt update -y && apt upgrade -y && sudo apt autoremove -y

    # available versions:    
    (skip) curl -s https://packages.cloud.google.com/apt/dists/kubernetes-xenial/main/binary-amd64/Packages | grep Version | awk '{print $2}'
    # curl -s https://packages.cloud.google.com/apt/dists/kubernetes-$(lsb_release -cs)/main/binary-amd64/Packages | grep Version | awk '{print $2}'
    # or
    (skip) apt list -a kubelet | head
    (skip) apt list -a kubeadm | head -n 10
    > Listing... Done
    > kubeadm/kubernetes-xenial,now 1.26.0-00 amd64 [installed]
    > kubeadm/kubernetes-xenial 1.25.5-00 amd64

    # install kubelet kubeadm kubectl
    sudo apt install -y --allow-change-held-packages kubelet kubeadm kubectl && sudo apt autoremove -y
    #
    # purge if needed:
    # sudo apt purge -y --allow-change-held-packages kubelet kubeadm kubectl && sudo apt autoremove -y

    # mark a package as held back, which will prevent the package from being automatically installed, upgraded or removed.
    apt-mark hold kubelet kubeadm kubectl

    ##########################################################################
    # Do NOT be attempted to run the following. `systemctl status kubelet` will fail before
    # `kubeadm init --control-plane-endpoint=ubuntu-k8s-master && chmod +r /etc/kubernetes/admin.conf`
    #
    # this will fix kubelet status failure issue:
    # chmod +r /etc/kubernetes/admin.conf
    # kubeadm init --control-plane-endpoint=ubuntu-k8s-master
    ##########################################################################

    # "(preferred) systemctl enable containerd" over "systemctl enable containerd.service":
    # For most service management commands, you can actually leave off the .service suffix
    # sudo systemctl enable containerd.service
    #
    systemctl enable kubelet
    systemctl start kubelet
    systemctl status kubelet

    # systemctl daemon-reload
    # systemctl restart kubelet.service
    # systemctl status kubelet.service

## 3. Create Kubernetes Cluster (On master node only)

    # 1. (on master) create cluster with kubeadm
    kubeadm init --control-plane-endpoint=ubuntu-k8s-master && chmod a+r /etc/kubernetes/admin.conf
    #
    k get nodes
    > ubuntu-k8s-master   NotReady   control-plane   7m10s   v1.26.0
    systemctl status kubelet
    >  Loaded: loaded (/lib/systemd/system/kubelet.service; enabled; preset: enabled)
    > Drop-In: /etc/systemd/system/kubelet.service.d
    >          |-- 10-kubeadm.conf
    cat /etc/systemd/system/kubelet.service.d/10-kubeadm.conf
    > ...
    journalctl -f -u kubelet

    # 2. get access to kubelet certs
    # if above command success: (else, go to `kubeadm reset`)
    # mkdir -p $HOME/.kube
    # cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
    # chown $(id -u):$(id -g) $HOME/.kube/config
    # or
    chmod a+r /etc/kubernetes/admin.conf
    export KUBECONFIG=/etc/kubernetes/admin.conf     ## do this in .bashrc
    #
    # 3. Important! install calio driver on master first before `kubeadm join`
    # run this on master BEFORE run `kubeadm join ... on worker`:
    #
    # (on master) Install network plugins. This must be done after call 'kubeadm init --control-plane-endpoint=ubuntu-k8s-master'
    # Install network plugins (Calico. --pod-network-cidr=192.168.0.0/16)
    # kubectl apply -f https://projectcalico.docs.tigera.io/manifests/calico.yaml
    # or
    cd /home/lma
    wget https://projectcalico.docs.tigera.io/manifests/calico.yaml
    kubectl apply -f calico.yaml
    #
    # (MetalLB only works with flannel, not calico) download `kube-falnnel.yaml`
    # cd /home/lma
    # wget https://raw.githubusercontent.com/flannel-io/flannel/v0.20.2/Documentation/kube-flannel.yml
    # kubectl apply -f kube-flannel.yml
    #
    # To verify network plugins:
    kubectl get pods -n kube-system
    kubectl get nodes
    #
    # 3. verify cluster
    kubectl cluster-info
    > Kubernetes control plane is running at https://ubuntu-k8s-master:6443
    > CoreDNS is running at https://ubuntu-k8s-master:6443/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy

## 4. Important! Install calio driver on master immediately after 'kubeadm init --control-plane-endpoint=ubuntu-k8s-master'

    # (on master) Install network plugins. This must be done after call 'kubeadm init --control-plane-endpoint=ubuntu-k8s-master'
    # Install network plugins (Calico. --pod-network-cidr=192.168.0.0/16)
    # kubectl apply -f https://projectcalico.docs.tigera.io/manifests/calico.yaml
    # or
    wget https://projectcalico.docs.tigera.io/manifests/calico.yaml
    kubectl apply -f calico.yaml
    # To verify network plugins:
    kubectl get pods -n kube-system
    kubectl get nodes

# 4.1 Replace Calico with Flannel for MetalLB (MetalLB only supports flannel. MetalLB does not support calico)

[FLANNEL VERSION]<https://github.com/flannel-io/flannel/releases/latest>

(on master) replace calio with flannel because MetalLB does not support calico

    # 1. delete worker nodes
    k get all
    k get nodes
    k drain ubuntu-k8s-worker1 --ignore-daemonsets
    k uncordon ubuntu-k8s-worker1
    k get all
    #
    # 2. download `calico.yaml`
    cd /home/lma
    wget https://projectcalico.docs.tigera.io/manifests/calico.yaml
    #
    # 3. download `kube-falnnel.yaml`
    # cd /home/lma
    # wget https://raw.githubusercontent.com/flannel-io/flannel/v0.20.2/Documentation/kube-flannel.yml
    #
    # 4. delete calico and install flannel
    ls -al
    kubectl delete -f calico.yaml
    kubectl apply -f kube-flannel.yml
    #
    # 5. rejoin worker
    # on master
    (on master) kubeadm token list
    kubeadm token delete <token-name>
    > bootstrap token "4x5v4m" deleted
    kubeadm token create --print-join-command
    > kubeadm join ubuntu-k8s-master:6443 --token cc1j74.czvbajxyf24zukxw --discovery-token-ca-cert-hash sha256:4693887603f8de153e2afb1dad4b893194b840945e4af3244f4fb376ea1b75ec

## 5. Join the cluster (On all worker nodes)

    # 1. Important! install **calio driver** on master immediately after 'kubeadm init --control-plane-endpoint=ubuntu-k8s-master'
    #
    # This may take up to 10 minutes for each worker:
    kubeadm join ubuntu-k8s-master:6443 --token 6g6w41.h0adjpr79jpo60ol \
        --discovery-token-ca-cert-hash sha256:4693887603f8de153e2afb1dad4b893194b840945e4af3244f4fb376ea1b75ec

    #
    # 2. in case the token has timed out (24 hours after creation of the token):
    #
    # on master
    (on master) kubeadm token list
    > connection refused
    kubeadm token delete <token-name>
    kubeadm token create --print-join-command
    > kubeadm join ubuntu-k8s-master:6443 --token cc1j74.czvbajxyf24zukxw --discovery-token-ca-cert-hash sha256:4693887603f8de153e2afb1dad4b893194b840945e4af3244f4fb376ea1b75ec
    #

    #
    # 3. error handling with `kubeadm join ubuntu-k8s-master:6443 --token ...`
    #
    # > [ERROR CRI]: container runtime is not running: output:
    # `kubeadm reset` must be run as root. `sudo su`
    (on worker as root) rm /etc/containerd/config.toml
    systemctl restart containerd
    kubeadm reset -f
    rm -rf /etc/cni/net.d
    ## `kubeadm init` will generate all those config files for kubeadm and cubelet
    kubeadm init
    kubeadm reset -f
    # join again
    kubeadm join ubuntu-k8s-master:6443 --token 4x5v4m.2a7gdvkgar9gmlhl --discovery-token-ca-cert-hash sha256:a0e8af24cda78fc3821c9b60d47d859b75724a841fe3a6b605dea92feb7cac6c

### Uninstall kubernetes completely

    #
    kubeadm token list --v=5
    kubeadm token list -v=5
    #
    kubelet --v=2
    kubelet -v=2

    # `kubeadm reset` must be run as root. `sudo su`
    (run as root) kubeadm reset -f
    apt purge -y kubeadm kubectl kubelet kubernetes-cni kube* --allow-change-held-packages
    apt purge -y containerd --allow-change-held-packages
    apt autoremove -y
    rm -rf ~/.kube
    Then, **restart** the computer. 

    # If status is NotReady for 'kubectl get nodes':
    # 1.delete '--network-plugin=cni' from /var/lib/kubelet/kubeadm-flags.env
    # cp /var/lib/kubelet/kubeadm-flags.env /var/lib/kubelet/kubeadm-flags.env.bak
    # sed -i "s/--network-plugin=cni *//g" /var/lib/kubelet/kubeadm-flags.env
    # or
    # vi /var/lib/kubelet/kubeadm-flags.env
    # delete --network-plugin=cni
    # 2. restart kubelet
    # systemctl daemon-reload
    # systemctl restart kubelet.service

## 6. Join the cluster with another control-plane

## 7. Reset

    # delete worker
    kubectl get nodes
    kubectl drain <node-name>
    kubectl drain ubuntu-k8s-worker1 --ignore-daemonsets
    kubectl uncordon ubuntu-k8s-worker1
    # or
    kubectl drain ubuntu-k8s-worker1 --ignore-daemonsets --delete-local-data
    kubectl delete node <node-name>
    kubectl delete node ubuntu-k8s-worker1
    (run this on worker: ) `sudo kubeadm reset -f`  ## `kubeadm reset` must be run as root. `sudo su`
    # run this on worker before re-join
    # `kubeadm reset` must be run as root. `sudo su`
    (run as root) kubeadm reset -f
    # then run kubeadm join on worker nodes
    kubeadm join ubuntu-k8s-master:6443 --token cc1j74.czvbajxyf24zukxw --discovery-token-ca-cert-hash sha256:4693887603f8de153e2afb1dad4b893194b840945e4af3244f4fb376ea1b75ec

### Join After 24 Hours of cluster init (toekn will be deleted automatically 24 hours after cluster init)

    (on master) kubeadm token list
    > connection refused
    kubeadm token delete <token-name>
    kubeadm token create --print-join-command

## Cluster status check

    kubectl cluster-info
    kubectl get nodes
    kubectl get nodes -o wide
    kubectl get pods
    kubectl get pods -o wide
    kubectl describe nodes
    kubectl get pods --all-namespaces
    kubectl get pods --all-namespaces -o wide
    kubectl get pods -n kube-system

## Namespace commands

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

## [Join new cluster worker](https://computingforgeeks.com/join-new-kubernetes-worker-node-to-existing-cluster/)

## Test Kubernetes

    # Run as user `lma`
    # 
    # run this as user `lma`
    (run as lma) sudo usermod -aG docker $USER
    sudo chmod a+rw /etc/kubernetes/admin.conf
    #
    kubectl create deployment nginx-app --image=nginx --replicas=2
    kubectl get deployment nginx-app
    kubectl expose deployment nginx-app --type=NodePort --port=80
    kubectl get svc nginx-app
    kubectl describe svc nginx-app
    # call worker1
    curl http://192.168.1.202:31866    ## use NodePort port number from `kubectl describe svc nginx-app`
    #
    # Expose deployments to the world:
    # Create a service (load balance) 
    kubectl create service nodeport nginx-deployment --tcp=80:80
    kubectl get svc
    kubectl get svc nginx-deployment -o yaml
    
    # kubectl delete servce nginx-deployment
    
    kubectl expose --help
    kubectl expose deployment nginx-deployment --port=80 --type=LoadBalancer
    kubectl describe service nginx-deployment
    
    kubectl get svc

## 7. Deploy to kubernetes

    # On master/control-plane node:
    kubectl get nodes
    kubectl get pods
    kubectl describe nodes
    kubectl get pods --all-namespaces
    kubectl get pods --all-namespaces -o wide
    
    # kubectl create deployment nginx-deployment --image=nginx:1.7.9 
    # Or
    # kubectl apply -f https://k8s.io/examples/controllers/nginx-deployment.yaml
    # Or
    curl -s -k -L -X GET "https://k8s.io/examples/controllers/nginx-deployment.yaml" -O
    kubectl create -f nginx-deployment.yaml
    
    kubectl get deployments
    kubectl get deployments --help
    kubectl get deployments -A
    
    kubectl rollout status deployment/nginx-deployment
    # get ReplicaSet
    kubectl get rs
    kubectl get pods --show-labels
    
    # Update nginx to 1.16.1
    kubectl --record deployment.apps/nginx-deployment set image deployment.v1.apps/nginx-deployment nginx=nginx:1.23.3
    # kubectl --record deployment.apps/nginx-deployment set image deployment.v1.apps/nginx-deployment nginx=nginx:latest
    # Or
    # kubectl set image deployment/nginx-deployment nginx=nginx:1.23.3 --record
    kubectl edit deployment.v1.apps/nginx-deployment
    kubectl rollout status deployment/nginx-deployment
    kubectl get pods
    kubectl describe deployments
    
    # Rolling Back a Deployment
    # Typo: 1.233 should be 1.23.3. Let us see how to roll back.
    kubectl set image deployment.v1.apps/nginx-deployment nginx=nginx:1.23.3 --record=true
    kubectl rollout status deployment/nginx-deployment
    # get ReplicaSet
    kubectl get rs
    kubectl get pods
    kubectl describe deployment
    # 1. First, check the revisions of this Deployment:
    kubectl rollout history deployment.v1.apps/nginx-deployment
    # 2. To see the details of each revision, run:
    kubectl rollout history deployment.v1.apps/nginx-deployment --revision=2
    # 3. Undo
    kubectl rollout undo deployment.v1.apps/nginx-deployment
    # Or
    # kubectl rollout undo deployment.v1.apps/nginx-deployment --to-revision=2
    # Check if the rollback was successful and the Deployment is running as expected, run:
    kubectl get deployment nginx-deployment
    # Get the description of the Deployment:
    kubectl describe deployment nginx-deployment
    
    kubectl delete deployment nginx-deployment
    
    # kubectl create deployment nginx-deployment image=nginx:1.7.9
    # kubectl delete deployment nginx-deployment
    
    kubectl get pods
    kubectl get pods -o wide
    # get to node, run: docker ps

## 8. Expose deployments to the world

    # Create a service (load balance) 
    kubectl create service nodeport nginx-deployment --tcp=80:80
    kubectl get svc
    kubectl get svc nginx-deployment -o yaml
    
    kubectl delete service nginx-deployment
    kubectl get svc
    
    kubectl expose --help
    kubectl expose deployment nginx-deployment --port=80 --type=LoadBalancer
    kubectl describe service nginx-deployment
    
    kubectl get svc
    # NAME               TYPE           CLUSTER-IP      EXTERNAL-IP   PORT(S)        AGE
    # nginx-deployment   LoadBalancer   10.96.131.123   <pending>     80:30621/TCP   6m5s
    
    # To solve 'EXTERNAL-IP'
    #
    # And this will not work:
    # kubectl patch svc nginx-deployment  -p '{"spec": {"type": "LoadBalancer", "externalIPs":["34.237.16.221"]}}'
    # This will not work either:
    # kubectl expose deployment nginx-deployment --port=80 --type=LoadBalancer --external-ip='34.237.16.221'
    
    # Makeshift:
    apt install -y nginx
    
    vi /etc/nginx/sites-available/default
    server {
        listen 80 default_server;
        listen [::]:80 default_server;
        location / {
            proxy_pass http://10.96.131.123
        }
    }
    
    # "(preferred) systemctl enable containerd" vs "systemctl enable containerd.service":
    # For most service management commands, you can actually leave off the .service suffix
    # sudo systemctl enable containerd.service
    #
    systemctl enable nginx
    systemctl start nginx
    systemctl status nginx
    
    # So that all traffic to the k8s-master are routed to CLUSTER-IP

## 9. Load balancer

    kubectl apply -f https://k8s.io/examples/service/load-balancer-example.yaml
    
    apiVersion: apps/v1
    kind: Deployment
    metadata:
      labels:
        app.kubernetes.io/name: load-balancer-example
      name: hello-world
    spec:
      replicas: 5
      selector:
        matchLabels:
          app.kubernetes.io/name: load-balancer-example
      template:
        metadata:
          labels:
            app.kubernetes.io/name: load-balancer-example
        spec:
          containers:
          - image: gcr.io/google-samples/node-hello:1.0
            name: hello-world
            ports:
            - containerPort: 8080


    kubectl get deployments hello-world
    kubectl describe deployments hello-world
    
    kubectl get replicasets
    kubectl describe replicasets
    
    kubectl expose deployment hello-world --type=LoadBalancer --name=my-service
    kubectl get services my-service
    kubectl describe services my-service
    
    curl http://<external-ip>:<port>

## 10. Clean up

    kubectl delete services my-service
    kubectl delete deployment hello-world
    # or
    kubectl delete -f hello-nginx-deployment.yml,hello-nginx-ingress.yml,hello-nginx-service.yml
    kubectl delete -f hello-nginx-deployment.yml -f hello-nginx-ingress.yml -f hello-nginx-service.yml

## [Scaling a Deployment](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/)

    kubectl scale deployment.v1.apps/nginx-deployment --replicas=10
    kubectl autoscale deployment.v1.apps/nginx-deployment --min=10 --max=15 --cpu-percent=80
    
    # Proportional scaling
    kubectl get deploy
    kubectl set image deployment.v1.apps/nginx-deployment nginx=nginx:sometag
    # get ReplicaSet
    kubectl get rs
    kubectl rollout pause deployment.v1.apps/nginx-deployment
    kubectl set image deployment.v1.apps/nginx-deployment nginx=nginx:1.23.3
    kubectl rollout history deployment.v1.apps/nginx-deployment
    kubectl set resources deployment.v1.apps/nginx-deployment -c=nginx --limits=cpu=200m,memory=512Mi
    kubectl rollout resume deployment.v1.apps/nginx-deployment
    # get ReplicaSet
    kubectl get rs -w
    kubectl get rs
    
    kubectl rollout status deployment/nginx-deployment
    
    kubectl patch deployment.v1.apps/nginx-deployment -p '{"spec":{"progressDeadlineSeconds":600}}'

## Controlling your cluster from machines other than the control-plane node

In order to get a kubectl on some other computer (e.g. laptop) to talk to your cluster, you need to copy the administrator `kubeconfig`
file from your control-plane node to your workstation like this:

    scp root@<control-plane-host>:/etc/kubernetes/admin.conf .
    kubectl --kubeconfig ./admin.conf get nodes 

## delete nodes

    k get nodes
    k drain <node-name>
    k uncordon <node-name>
    k drain ubuntu-k8s-worker1 --ignore-daemonsets
    k uncordon ubuntu-k8s-worker1

## Proxying API Server to localhost

If you want to connect to the API Server from outside the cluster you can use kubectl proxy:

    scp root@<control-plane-host>:/etc/kubernetes/admin.conf .
    kubectl --kubeconfig ./admin.conf proxy    

## nano

    nano deploy.yml

    apiVersion: apps/v1
    kind: Deployment
    metadata:
       name: nginx
    spec:
       replicas: 3
       selector:
          matchLabels:
             app: nginx
       template:
          metadata:
             labels:
                app: nginx
          spec:
             containers:
             - name: nginx
               image: nginx:1.7.9
               ports:
               - containerPort: 80

############################################################
######## Following are informational, not necessary ########
############################################################

## For local nginx, append the following to the end of D:\01-AppServers\nginx-1.19.7\conf\nginx.conf

## For master:  192.168.3.200

## For worker1: 192.168.3.201

    stream {
        server {
            listen     22;
            proxy_pass 192.168.3.201:22;
        }

        server {
            listen     6443;
            proxy_pass 192.168.3.201:6443;
        }
       
        server {
            listen     2379;
            proxy_pass 192.168.3.201:2379;
        }
       
        server {
            listen     2380;
            proxy_pass 192.168.3.201:2380;
        }
       
        server {
            listen     10250;
            proxy_pass 192.168.3.201:10250;
        }
       
        server {
            listen     10251;
            proxy_pass 192.168.3.201:10251;
        }
       
        server {
            listen     10252;
            proxy_pass 192.168.3.201:10252;
        }
       
        server {
            listen     10255;
            proxy_pass 192.168.3.201:10255;
        }
    }

## Ports

Control Plane:

    Protocol    Direction    Port Range    Purpose                    Used By
    TCP         Inbound      6443          Kubernetes API server      All
    TCP         Inbound      2379-2380     etcd server client API     kube-apiserver, etcd
    TCP         Inbound      10250         Kubelet API                Self, Control plane
    TCP         Inbound      10259         kube-scheduler             Self
    TCP         Inbound      10257         kube-controller-manager    Self

Worker Node(s):

    Protocol    Direction    Port Range    Purpose                    Used By
    TCP         Inbound      10250         Kubelet API                Self, Control plane
    TCP         Inbound      30000-32767   NodePort Services          All

## Install `docker.io` or `docker-ce`, any one of them

1. (skip) `docker.io` is maintained by Debian/Ubuntu
2. (select this) `docker-ce` is maintained by Docker Inc.

### Install `docker-ce`

    sudo apt install -y apt-transport-https ca-certificates curl software-properties-common

    sudo apt update -y
    sudo apt install -y ca-certificates curl gnupg lsb-release
    sudo mkdir -p /etc/apt/keyrings
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
    
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
          $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
    
    sudo apt update
    sudo chmod a+r /etc/apt/keyrings/docker.gpg
    sudo apt update
    sudo apt install docker-ce docker-ce-cli containerd.io docker-compose-plugin


    apt-cache policy docker-ce
    sudo apt install -y docker-ce
    sudo systemctl status docker
    sudo usermod -aG docker ${USER}
    # sudo dnf config-manager --add-repo=https://download.docker.com/linux/centos/docker-cd.repo
    # dnf install -y docker-ce -nobest --allowerasing
    # sudo systemctl enable --now docker
    sudo usermod -aG docker $USER

    sudo apt install -y docker-compose

## Configure docker

    sudo tee /etc/docker/daemon.json << EOF
    {
      "exec-opts": ["native.cgroupdriver=systemd"],
      "log-driver": "json-file",
      "log-opts": {
        "max-size": "100m"
      },
      "storage-driver": "overlay2"
    }
    EOF

    # "(preferred) systemctl enable containerd" vs "systemctl enable containerd.service":
    # For most service management commands, you can actually leave off the .service suffix
    # sudo systemctl enable containerd.service
    #
    sudo systemctl enable docker.service
    sudo systemctl start docker.service
    sudo systemctl status docker.service

## Uninstall `docker-ce`

    dpkg -l | grep -i docker
    sudo apt purge -y docker-ce
    sudo apt purge -y docker-compose

## Use `docker-compose`

    cd /home/lma/LukeMa/03-lukema/docker-logging/my-properties-boot-logger
    # username: xxlukema/Cfg-
    sudo docker login
    sudo docker-compose up
