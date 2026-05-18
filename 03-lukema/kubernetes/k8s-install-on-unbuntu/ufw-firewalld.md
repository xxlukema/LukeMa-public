# `ufw` and `firewalld`

- `ufw` for ubuntu
- `firewalld` for centos, also works on ubuntu

## Commands

    #############################################
    # ufw: for ubuntu
    #############################################
    sudo ufw status verbose
    sudo ufw disable
    sudo ufw status verbose
    # cat /etc/default/ufw
    # sudo ufw default deny incoming
    # sudo ufw default allow outgoing
    # cat /etc/services
    # sudo ufw allow ssh
    # sudo ufw allow 22
    # sudo ufw allow 2222
    # sudo ufw allow 80
    # sudo ufw allow 443
    # sudo ufw enable
    # sudo ufw disable
    # sudo ufw allow 6000:6007/tcp
    # sudo ufw allow 6000:6007/udp
    # sudo ufw allow from 203.0.113.4
    # sudo ufw allow from 203.0.113.4 to any port 22
    # sudo ufw allow from 203.0.113.0/24
    # sudo ufw allow from 203.0.113.0/24 to any port 22
    # ip addr
    # sudo ufw allow in on eth0 to any port 80
    # sudo ufw allow in on eth1 to any port 3306
    # sudo ufw deny http
    # sudo ufw deny from 203.0.113.4
    # sudo ufw status numbered
    # sudo ufw delete 2
    # sudo ufw delete allow http
    # sudo ufw delete allow 80
    # sudo ufw status verbose
    # sudo ufw disable
    # sudo ufw reset

    #
    #############################################
    # firewalld: for redhat sentos
    #############################################
    # https://computingforgeeks.com/install-and-use-firewalld-on-ubuntu/
    # sudo apt install -y firewalld
    # sudo firewall-cmd --state
    # sudo ufw disable
    # sudo firewall-cmd --list-all
    # sudo firewall-cmd --get-services
    # sudo firewall-cmd --add-service=http --permanent
    # sudo firewall-cmd --add-service={http,https} --permanent
    # sudo firewall-cmd --add-port=514/udp --permanent
    # sudo firewall-cmd --new-zone=myzone --permanent
    # sudo firewall-cmd --zone=myzone --add-port=4567/tcp --permanent
    # sudo firewall-cmd --get-zone-of-interface=eth0 --permanent
    # sudo firewall-cmd --zone=<zone> --add-interface=eth0 --permanent
    # Allow access to ssh from 192.168.0.12 sing IP address
    # sudo firewall-cmd --add-rich-rule 'rule family="ipv4" service name="ssh" \
    # source address="192.168.0.12/32" accept' --permanent
    #
    # Allow access to ssh from 10.1.1.0/24 network
    # sudo firewall-cmd --add-rich-rule 'rule family="ipv4" service name="ssh" \
    # source address="10.1.1.0/24" accept' --permanent
    # sudo firewall-cmd --list-rich-rules
    # Enable masquerading
    # sudo firewall-cmd --add-masquerade --permanent
    #
    # Port forward to a different port within same server ( 22 > 2022)
    # sudo firewall-cmd --add-forward-port=port=22:proto=tcp:toport=2022 --permanent
    #
    # Port forward to same port on a different server (local:22 > 192.168.2.10:22)
    # sudo firewall-cmd --add-forward-port=port=22:proto=tcp:toaddr=192.168.2.10 --permanent
    #
    # Port forward to different port on a different server (local:7071 > 10.50.142.37:9071)
    # sudo firewall-cmd --add-forward-port=port=7071:proto=tcp:toport=9071:toaddr=10.50.142.37 --permanent
    # Removing port/service:
    # Replace --add with –-remove
