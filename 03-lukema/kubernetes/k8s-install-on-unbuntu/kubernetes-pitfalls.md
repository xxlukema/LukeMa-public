# kubernetes pitfalls

    kubelet --v=2
    kubelet -v=2

    journalctl -f -u kubelet

## Install

    # swap should be disabled permanently

## After `apt install -y Install kubelet kubeadm kubectl`, do not be attempted to run `systemctl status kubelet`

- Do NOT be attempted to run `systemctl status kubelet` after install kubelet. Th network is not ready until `cubeadm init` is executed

## Troubleshooting `systemctl status kubelet`

    systemctl status kubelet
    kubelet -v=2
    kubelet --v=2
    sudo journalctl -f -u kubelet

    # to uninstall kubelet kubeadm kubectl
    apt purge -y kubelet kubeadm kubectl && sudo apt autoremove -y

    mkdir -p /etc/containerd && containerd config default > /etc/containerd/config.toml
    # crictl config --set runtime-endpoint=unix:///run/containerd/containerd.sock --set image-endpoint=unix:///run/containerd/containerd.sock
    # crictl config --set runtime-endpoint=unix:///run/containerd/containerd.sock

    #
    cat /etc/systemd/system/kubelet.service.d/10-kubeadm.conf
    > EnvironmentFile=-/etc/default/kubelet
    cat /etc/default/kubelet
    > KUBELET_CONFIG_ARGS
    > KUBELET_EXTRA_ARGS="--container-runtime-endpoint=unix:///run/containerd/containerd.sock"
    # if not found /etc/default/kubelet
    vi /etc/default/kubelet
    # add the following lines to /etc/default/kubelet
    KUBELET_CONFIG_ARGS=
    KUBELET_EXTRA_ARGS="--container-runtime-endpoint=unix:///run/containerd/containerd.sock"

    sudo systemctl status kubelet
    > Main PID: 19322 (code=exited, status=1/FAILURE)
    # debug kubelet status
    sudo journalctl -f -u kubelet
    kubelet -v=2
    # or
    kubelet --v=2

    #
    cd /etc/kubernetes/pki
    kubeadm init phase certs all --apiserver-advertise-address 192.168.1.201
    journalctl -f -u kubelet
    cd /etc/kubernetes
    kubeadm init phase kubeconfig all
    journalctl -f -u kubelet
    systemctl restart kubelet
    journalctl -f -u kubelet

    cat /etc/systemd/system/kubelet.service.d/10-kubeadm.conf
    > # Note: This dropin only works with kubeadm and kubelet v1.11+
    > [Service]
    > Environment="KUBELET_KUBECONFIG_ARGS=--bootstrap-kubeconfig=/etc/kubernetes/bootstrap-kubelet.conf --kubeconfig=/etc/kubernetes/kubelet.conf"
    > Environment="KUBELET_CONFIG_ARGS=--config=/var/lib/kubelet/config.yaml"
    > # This is a file that "kubeadm init" and "kubeadm join" generates at runtime, populating the KUBELET_KUBEADM_ARGS variable dynamically
    > EnvironmentFile=-/var/lib/kubelet/kubeadm-flags.env
    > # This is a file that the user can use for overrides of the kubelet args as a last resort. Preferably, the user should use
    > # the .NodeRegistration.KubeletExtraArgs object in the configuration files instead. KUBELET_EXTRA_ARGS should be sourced from this file.
    > EnvironmentFile=-/etc/default/kubelet
    > ExecStart=
    > ExecStart=/usr/bin/kubelet $KUBELET_KUBECONFIG_ARGS $KUBELET_CONFIG_ARGS $KUBELET_KUBEADM_ARGS $KUBELET_EXTRA_ARGS

    # cat /var/lib/kubelet/kubeadm-flags.env
    # > KUBELET_KUBEADM_ARGS="--container-runtime-endpoint=unix:///run/containerd/containerd.sock"

## Errors with `kubectl cluster-info`

    kubectl cluster-info
    > To further debug and diagnose cluster problems, use 'kubectl cluster-info dump'.
    > The connection to the server ubuntu-k8s-master:6443 was refused - did you specify the right host or port?
    #
    # to fix the above error
    # option 1:
    rm -rf ~/.kube
    chmod a+rw /etc/kubernetes/admin.conf
    # option 2:
    sudo -i
    # turn off swap
    swapoff -a
    # two steps to turn off swap permenantly:
    # step 1/2. remove swapfile
    sudo rm /swapfile
    # step 2/2. remove following line from /etc/fstab
    vi /etc/fstab
    # comment out this line:
    /swapfile       none    swap    sw      0       0
    #
    # reload to apply above changes
    sysctl --system
    #
    # strace -eopenat kubectl version
    #
    #############################################
    # disk space
    #############################################
    df
    df -h
    du
    du -h
    #

    # verify cluster again
    kubctl cluster-info

## **Important!** install calio driver on master first before `kubeadm join`

1. install `containerd.io`
2. install `kubeadm`, `kubelet`, `kubectl`
3. Important! install calio driver on master first
4. run `kubeadm join` on workers

## (on master as root) `kubeadm reset -f` - In case `kubeadm init --control-plane-endpoint=ubuntu-k8s-master` Fails

    # if `kubeadm init --control-plane-endpoint=ubuntu-k8s-master` throws the following error:
    > [init] Using Kubernetes version: v1.26.0
    > [preflight] Running pre-flight checks
    > error execution phase preflight: [preflight] Some fatal errors occurred:
    >         [ERROR FileAvailable--etc-kubernetes-manifests-kube-apiserver.yaml]: /etc/kubernetes/manifests/kube-apiserver.yaml already exists
    # If you see the above error, to run 'kubeadm init' again, you must tear down the cluster:
    # To run 'kubeadm init' again, you must tear down the cluster on master:
    # kubectl config delete-cluster
    # `kubeadm reset` must be run as root. `sudo su`
    # kubeadm reset -f
    # run the following on master
    kubectl config delete-cluster
    # `kubeadm reset` must be run as root. `sudo su`
    #
    #
    kubeadm reset -f
    rm -rf /etc/cni/net.d
    ipvsadm --clear
    # cleanup iptables ipv4
    iptables -P INPUT ACCEPT
    iptables -P FORWARD ACCEPT
    iptables -P OUTPUT ACCEPT
    iptables -t nat -F
    iptables -t mangle -F
    iptables -F
    iptables -X
    # cleanup iptables ipv6
    ip6tables -P INPUT ACCEPT
    ip6tables -P FORWARD ACCEPT
    ip6tables -P OUTPUT ACCEPT
    ip6tables -t nat -F
    ip6tables -t mangle -F
    ip6tables -F
    ip6tables -X
    # verify
    iptables -nvL
    #
    #
    > Chain INPUT (policy ACCEPT 0 packets, 0 bytes)
    >  pkts bytes target     prot opt in     out     source               destination
    > Chain FORWARD (policy ACCEPT 0 packets, 0 bytes)
    >  pkts bytes target     prot opt in     out     source               destination
    > Chain OUTPUT (policy ACCEPT 0 packets, 0 bytes)
    >  pkts bytes target     prot opt in     out     source               destination
    sudo rm /etc/containerd/config.toml
    sudo systemctl restart containerd
    
    # Sometimes, error from `kubeadm init` or `kubeadm join` can be solved by:
    # echo 1 > /proc/sys/net/ipv4/ip_forward

## (on workers) error handling with `kubeadm join ubuntu-k8s-master:6443 --token ...`

    #
    # > [ERROR CRI]: container runtime is not running: output:
    # `kubeadm reset` must be run as root. `sudo su`
    (on workers as root) rm /etc/containerd/config.toml
    systemctl restart containerd
    kubeadm reset -f
    rm -rf /etc/cni/net.d
    ## `kubeadm init` will generate all those config files for kubeadm and cubelet
    kubeadm init
    kubeadm reset -f
    # join again
    kubeadm join ubuntu-k8s-master:6443 --token 4x5v4m.2a7gdvkgar9gmlhl --discovery-token-ca-cert-hash sha256:a0e8af24cda78fc3821c9b60d47d859b75724a841fe3a6b605dea92feb7cac6c
