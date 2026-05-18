# VirtualBox with Vagrant Conflicts with Docker

    # vagrant default user: vagrant
    # vagrant default passwd: vagrant
    # new password: xiao.hai.zi
    vagrant ssh
    
## [Hyper-V Limited Networking]<https://www.vagrantup.com/docs/providers/hyperv/limitations>

Vagrant does not yet know how to create and configure new networks for Hyper-V. When launching a machine with Hyper-V, Vagrant
will prompt you asking what virtual switch you want to connect the virtual machine to.

A result of this is that networking configurations in the `Vagrantfile` are completely ignored with Hyper-V. Vagrant cannot enforce a static IP or
automatically configure a NAT.

However, the IP address of the machine will be reported as part of the vagrant up, and you can use that IP address as if it were a host only network.

## Resolution to Host Kubernetes Using Vagrant

- 1. Because Hyper-V does not support static IP (on public_network), other hosts cannot connect to `vagrant up` on this host, this configuration
     of vagrant cannot be used to host kubernetes.
- 2. Vagrant on VirtualBox: k8s control plane (master) requires 2 CPUs minimum, but vagrant on master will timeout for 2 cpus for
     "hashicorp/bionic64" (basic Ubuntu 18.04 64-bit box).
- 3. For public_network, my experience is to use host subnet (192.168.1.0/8). Otherwise, the vagrant might not be accessible from another host laptop.
- 4. config.vm.network "public_network", ip: "192.168.1.200"
- 5. It is possible to launch luke-k8s-master and luke-k8s-worker1 on the same host laptop.
- 6. Vagrant on Hyper-V can support 2 CPUs for "bento/ubuntu-20.04". Installation fails with "sudo systemctl status kubelet.service" with "exit_status=1/FAILURE".

## 1. VirtualBox with Vagrant Conflicts with Docker

When docker is installed, running `vagrant up` with `VirtualBox` has port conflicts with docker. Therefore, run `vagrant up` with `hyper-v` as of 2022-04-30.

## 2. (Optional if VirtualBox is installed. Mandatory if VirtualBox is not installed.) Enable Hyper-V and SMB ('SMB 1.0/CIFS Server' is optional.)

**VirtualBox is not required!!!**

- Without `VirtualBox`, Run `vagrant up --provider=hyperv` requires admin privilege.

### `vagrant up --provider=hyperv` will ask for the following information:

    # vagrant up --provider=hyperv
    ...
    default: Please choose a switch to attach to your Hyper-V instance.
    default: If none of these are appropriate, please open the Hyper-V manager
    default: to create a new virtual switch.
    default:
    default: 1) Default Switch
    default: 2) WSL
    default:
    default: What switch would you like to use? 1
    ...
    default: You will be asked for the username and password to use for the SMB
    default: folders shortly. Please use the proper username/password of your
    default: account.
    default:
    default: Username (user[@domain]): lukema (windows username)
    default: Password (will be hidden): xiao.peng.you (windows passwd)
    ...
    # Connect to vagrant
    vagrant ssh
    # k8s master requires 2 cpus 2GB memory minimum.
    cat /proc/meminfo
    # Or
    free -m
    nproc
    sudo apt update -y && sudo apt full-upgrade -y
    # Or
    sudo apt update -y && sudo apt upgrade -y && sudo apt autoremove -y
    sudo apt update -y
    sudo apt upgrade -y
    sudo apt autoremove -y
    sudo apt full-upgrade -y
    lsb_release -a
    # `ifconfig` has been deprecated. Use `ip -c a` instead.

## Private IP Numbers

    Prefix          First Address   Last Address      Number of Addresses
    10.0.0.0/8      10.0.0.0        10.255.255.255    16,777,216
    172.16.0.0/12   172.16.0.0      172.31.255.255    1,048,576
    192.168.0.0/16  192.168.0.0     192.168.255.255   65,536





