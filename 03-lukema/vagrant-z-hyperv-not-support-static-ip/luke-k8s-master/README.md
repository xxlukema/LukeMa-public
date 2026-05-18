# vagrant up

## 1. Fix of "umount: /mnt: not mounted"

    vagrant plugin uninstall vagrant-vbguest
    vagrant plugin install vagrant-vbguest --plugin-version 0.21

## 2. Add the following to `C:\Windows\System32\drivers\etc\hosts`:

    #
    # 192.168.3.1 Default Router IP Login. Reserved. Do not use.
    # 192.168.3.* will cause ssh timeout with `vagrant up`. Do not know the reason yet
    #
    192.168.1.200    luke-k8s-master    luke-k8s-master.learn.com
    192.168.1.201    luke-k8s-worker1   luke-k8s-worker1.learn.com
    192.168.1.100    lukevm_home        lukevm_home.learn.com
    192.168.1.101    lukevm_test        lukevm_test.learn.com

## 3. default user

    vagrant/vagrant
    change passwd to xiao.peng.you

## 4. `/etc/hosts` ports:

    luke-k8s-master:  192.168.1.200
    luke-k8s-worker1: 192.168.1.201
