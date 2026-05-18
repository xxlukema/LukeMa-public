# SNMP `ro` and `rw` access

- `SNMPv3` uses `USM` for Authentication and Message Privacy.
- `SNMPv1` and `SNMPv2c` do not use `USM` for Authentication and Message Privacy.

## SNMPv1 and SNMPv2c both use `community` to control `ro/rw`

    # `sudo vi /etc/snmp/snmpd.conf`
    # SNMPv3 does not use community. users need to be created with what they can view with `rouser/rwuser` lines in `/etc/snmp/snmpd.conf`
    #             sec.name         source          community
    com2sec       notConfigUser    default         public
    rocommunity   read             localhost
    rwcommunity   write            localhost,192.168.1.10/24

## SNMPv3 uses `rouser/rwuser` to control `ro/rw`

    # create `rouser/rwuser`:
    # `snmpd` must be stopped to create `rouser/rwuser`:
    sudo systemctl stop snmpd
    #
    # `net-snmp-config` is in `net-snm-devel` for CentOS/aws, or in `libsnmp-dev` for ubuntu
    net-snmp-config --create-snmpv3-user
    # or
    sudo yum install net-snmp-create-v3-user
    sudo systemctl stop snmpd
    sudo net-snmp-create-v3-user
    > Enter a SNMPv3 user name to create: 
    > luke
    > Enter authentication pass-phrase: 
    > ChangeMe
    > Enter encryption pass-phrase: 
    >   [press return to reuse the authentication pass-phrase]
    > 
    > adding the following line to /var/lib/snmp/snmpd.conf:
    >    createUser luke MD5 "ChangeMe" DES
    > adding the following line to /etc/snmp/snmpd.conf:
    >    rwuser luke
    sudo systemctl start snmpd

    # `vi ~/.snmp/snmp.conf` --- SNMPv3
    # SNMPv3 does not use community. users need to be created with what they can view with `rouser/rwuser` lines in `/etc/snmp/snmpd.conf`
    # add these lines
    defVersion 3
    defSecurityLevel authPriv
    defSecurityName luke
    defAuthPassphrase ChangeMe
    defPrivPassphrase ChangeMe
    # or use defPassphrase if defAuthPassphrase and defPrivPassphrase have the same value
    # defPassphrase ChangeMe
