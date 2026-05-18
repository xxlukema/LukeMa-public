# AWS EC2 Aws Linux

## AWS

Luke-key
c:/D/Tools/Luke-key-2024-02-02.ppk

i-03e27b1c98b9c2d5d (hello-shein)
PublicIPs: 52.3.85.231    PrivateIPs: 172.31.86.122

Elastic Ip: 52.3.85.231
eipalloc-02470b266ef32909f
Associated with the ec2 instance.

<https://52.3.85.231:8443/swagger-ui/index.html>

<ec2-user@52.3.85.231>
<https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/putty.html>

### Configure putty

1. putty ip: <ec2-user@52.3.85.231> or <ec2-user@ec2-52-3-85-231.compute-1.amazonaws.com>
2. Connection :: SSH :: Auth :: Credentials :: Private key file for authentication: Browse for the generated `Luke-key-2024-02-02.ppk` file

### Configure WinSCP

## Info

    # ubuntu
    (ubuntu) dpkg –print architecture

    # centos
    uname -a
    uname -m
    > x86_64

    arch
    > x86_64

    lscpu

    grep ^NAME  /etc/*release
    > /etc/os-release:NAME="Amazon Linux"

## What is Amazon Linux 2023 based on?

Major releases of Amazon Linux will be based in part on the current version of the upstream Fedora Linux distribution, though Amazon may choose to add or replace
specific packages from other non-Fedora upstreams (e.g. Linux kernel is sourced from kernel.org’s Long Term Support choices and is maintained specifically for
Amazon's Linux products).

While Amazon Linux 2 was based on Red Hat Enterprise Linux (RHEL) 7, each major release of Amazon Linux 2023 will use a combination of Fedora Linux and CentOS Stream.

## Edit EC2 instance/VPC firewall security group to allow access from any source to connect to `PostgreSQL`, `MongoDB`, and `Neo4j`

### Step 1. Edit EC2 instance/VPC firewall security group

The same security group has been bound to both EC2 instance and VPC

    # 10.2 open firewall for the EC2 instance:
    AWS Console :: EC2 Instance :: (click on the "Name" or "Instance ID" of the EC2 instance) :: (Instance section) Security (Tab)
                :: (click on) Security groups name (ex. sg-022bcfb0e489b3083 (launch-wizard-5) or launch-wizard-5)
                :: Inbound rules (Tab) :: Edit Inbound rules (button) :: Add rule (button) :: (add)
                      Type: Custom TCP | Protocol: TCP | Port range: 27017 (for mongodb, or 5432 for PostgreSQL) | Source: 0.0.0.0/0 | Description: MongoDB
                :: Save rules (button)

    # There is no need to edit "Outbound rules". Use default: allow IPv4 | All traffic | All protocol | All port range | All destination: 0.0.0.0/0

### Step 2. Edit `/etc/mongod.conf` to allow connection from all Ip

    # step 1. edit `/ect/mongod.conf`
    sudo vi /etc/mongod.conf
    # add
    # network interfaces
    net:
      port: 27017
      # (default) bindIp: 127.0.0.1  # Enter 0.0.0.0,:: to bind to all IPv4 and IPv6 addresses or, alternatively, use the net.bindIpAll setting.
      # bindIp: 127.0.0.1,mongodb_server_ip
      # bindIp: 127.0.0.1, 52.3.85.231
      bindIpAll: true   # <========= new for lukedb

    # step 2. restart mongod service
    sudo systemctl restart mongod
    sudo systemctl status mongod

## Install PostgreSQL

[Medium How to install postgresql to AL2023]<https://hbayraktar.medium.com/how-to-install-postgresql-15-on-amazon-linux-2023-a-step-by-step-guide-57eebb7ad9fc>

    # 1. update
    sudo dnf update

    # 2. check lastest version number of postgresql: [AL2023 All Packages]<https://docs.aws.amazon.com/linux/al2023/release-notes/all-packages-AL2023.2.html>
    sudo dnf install postgresql15.x86_64 postgresql15-server -y

    # 3. init db
    sudo postgresql-setup --initdb
    > * Initializing database in '/var/lib/pgsql/data'
    > * Initialized, logs are in /var/lib/pgsql/initdb_postgresql.log

    # 4. enable service
    sudo systemctl start postgresql
    sudo systemctl enable postgresql
    sudo systemctl status postgresql

    # 5. configure PostgreSQL
    # Change the ssh user password:
    sudo passwd postgres
    sheingres
    
    # Log in using the Postgres system account:
    su - postgres
    
    # Now, change the admin database password:
    psql -c "ALTER USER postgres WITH PASSWORD 'sheingres';"

    psql -c "select now();" -- date and time
    psql -c "select current_date;" -- date
    psql -c "select current_time;" -- time

    exit

    # after `su - postgres`, use `psql` to connect to db server
    psql

    postgres=# <============ new command prompt
    postgres-# <============ continuing command prompt. Use \? for help or press control-C to clear the input buffer.

    help
    \help
    \?

    -- list databases
    \l
    \l+
    SELECT datname FROM pg_database;

    -- change database
    \c test

    -- list tables
    \dt

    -- list users
    \du

    # 6. backup `postgresql.conf`
    (ec2-user) sudo cp /var/lib/pgsql/data/postgresql.conf /var/lib/pgsql/data/postgresql.conf.bak

    # 7. conf
    sudo vi /var/lib/pgsql/data/postgresql.conf
    # By default, PostgreSQL only listens to localhost
    listen_addresses = 'localhost'
    # if you want to listen all IP addresses:
    listen_addresses = '*' # what IP address(es) to listen on;

    # 8. authentication
    sudo cp /var/lib/pgsql/data/pg_hba.conf /var/lib/pgsql/data/pg_hba.conf.bak
    sudo vi /var/lib/pgsql/data/pg_hba.conf
    # You can change ident as md5 To allow connections from absolutely any address with password authentication
    host     all     all     0.0.0.0/0     md5
    # OR
    sudo sed -i 's/ident$/md5/' /var/lib/pgsql/data/pg_hba.conf

    # 9. How to Create a User & Database
    # Connect to the PostgreSQL server as the Postgres user:
    sudo -i -u postgres psql
    
    # Create a new database user:
    CREATE USER your-username WITH PASSWORD 'password';
    
    # Create a new database:
    CREATE DATABASE database_name;
    
    # Grant all privileges on the database to the user:
    GRANT ALL PRIVILEGES ON DATABASE database_name TO your-username;
    
    # To list all available PostgreSQL users and databases:
    \l

    # sudo systemctl stop postgresql
    # sudo systemctl disable postgresql
    # sudo dnf remove purge postgresql15.x86_64 postgresql15-server
    # sudo rm -rf /var/lib/pgsql /var/log/postgresql /etc/postgresql

## Install `MongoDB`

[Install]<https://www.mongodb.com/docs/manual/tutorial/install-mongodb-on-amazon/>

    # 1. add yum repo
    sudo vi /etc/yum.repos.d/mongodb-org-7.0.repo
    # add the following lines to repo:
    [mongodb-org-7.0]
    name=MongoDB Repository
    baseurl=https://repo.mongodb.org/yum/amazon/2023/mongodb-org/7.0/x86_64/
    gpgcheck=1
    enabled=1
    gpgkey=https://pgp.mongodb.com/server-7.0.asc

    # 2. install
    sudo yum install -y mongodb-org
    # (or) sudo yum install -y mongodb-org-7.0.5 mongodb-org-database-7.0.5 mongodb-org-server-7.0.5 mongodb-mongosh-7.0.5 mongodb-org-mongos-7.0.5 mongodb-org-tools-7.0.5

    # 3. determine which system
    ps --no-headers -o comm 1
    > systemd

    # 4. start mongodb
    sudo systemctl start mongod
    #
    # If you receive an error similar to the following when starting mongod:
    # > Failed to start mongod.service: Unit mongod.service not found.
    # Run the following command first:
    # sudo systemctl daemon-reload

    sudo systemctl enable mongod
    sudo systemctl status mongod

    # 5. connect using `mongosh`
    monosh
    > mongosh: OpenSSL configuration error:
    > 001908B0DC7F0000:error:030000A9:digital envelope routines:alg_module_init:unknown option:../deps/openssl/openssl/crypto/evp/evp_cnf.c:61:name=rh-allow-sha1-signatures, value=yes
    # fix to the above error:
    # (not work) sudo yum remove mongodb-mongosh -y
    # (not work) sudo yum install mongodb-mongosh-shared-openssl3 -y
    # (not work) sudo yum install mongodb-mongosh -y
    # (tested and work) remove and purge `mongodb-org` and reinstall (step 12)
    # sudo service mongod stop
    # sudo yum remove purge mongodb-org
    # (or) sudo yum erase $(sudo rpm -qa | grep mongodb-org)
    # sudo rm -r /var/log/mongodb
    # sudo rm -r /var/lib/mongo

    # successful connect:
    mongosh
    > Current Mongosh Log ID: 65bdf9460b1356e72f0b61ba
    > ...
    > test>

    # connect from laptop
    mongsh mongodb://luke:luke@52.3.85.231:27017/?authSource=lukedb -u luke -p luke

    # 6. Create database `lukedb` if it does not exist, or switch to `lukedb` if it exists.
    use lukebs

    # 7. help
    help
    show databases
    show dbs
    show tables
    show users

    #
    # 8. Create user `luke/luke` with `readWrite` permission to `lukedb`.
    db.createUser(
      {
        user: "luke",
        pwd:  "luke",   // or passwordPrompt()
        roles: [
                 { role: "readWrite", db: "lukedb" },
                 { role: "read", db: "test" }
               ]
      }
    )
    #

    # 9. config
    # run `sudo systemctl status mongod` will display config file:
    sudo systemctl status mongod
    > ...
    >      CGroup: /system.slice/mongod.service
    >        └─75671 /usr/bin/mongod -f /etc/mongod.conf
    sudo vi /etc/mongod.conf
    # add:
    security:
      authorization: enabled
    # then restart monogod

    # 10. allow connection from outside
    # 10.1 `sudo vi /etc/mongod.conf`
    sudo vi /etc/mongod.conf
    # add:
    # network interfaces
    net:
      port: 27017
      # bindIp: 127.0.0.1,mongodb_server_ip
      bindIp: 127.0.0.1, 52.3.85.231

    # 10.2 open firewall for the EC2 instance:
    AWS Console :: EC2 Instance :: (click on the "Name" or "Instance ID" of the EC2 instance) :: (Instance section) Security (Tab)
                :: (click on) Security groups name (ex. sg-022bcfb0e489b3083 (launch-wizard-5) or launch-wizard-5)
                :: Inbound rules (Tab) :: Edit Inbound rules (button) :: Add rule (button) :: (add)
                      Type: Custom TCP | Protocol: TCP | Port range: 27017 (for mongodb, or 5432 for PostgreSQL) | Source: 0.0.0.0/0 | Description: MongoDB
                :: Save rules (button)

    # There is no need to edit "Outbound rules". Use default: allow IPv4 | All traffic | All protocol | All port range | All destination: 0.0.0.0/0

    # 11. Shutdown the server and exit
    db.shutdownServer()
    exit
    sudo systemctl restart mongod

    # 12. remove mongodb:
    # sudo service mongod stop
    # sudo yum remove purge mongodb-org
    # (or) sudo yum erase $(sudo rpm -qa | grep mongodb-org)
    # sudo rm -r /var/log/mongodb
    # sudo rm -r /var/lib/mongo

    # 12. Enable TLS/SSL:
    sudo vi /etc/mongod.conf
    # add:
    net:
      tls:
        mode: requireTLS
        certificateKeyFile: /etc/ssl/mongodb.pem
        CAFile: /etc/ssl/caToValidateClientCertificates.pem
        allowConnectionsWithoutCertificates: true  ## <====== if `allowConnectionsWithoutCertificates: true`, client can skip cert file.

    mongosh --config <path/to/configuration/file>  ## <====== if `allowConnectionsWithoutCertificates: true`, client can skip cert file.

## Install `Noe4j`

[Install]<https://neo4j.com/docs/operations-manual/current/installation/linux/rpm/>

Neo4j port: 7687
uri: <neo4j://localhost:7687>

    # 1. add yum repo
    sudo vi /etc/yum.repos.d/neo4j.repo
    # add the following lines to repo:
    [neo4j]
    name=Neo4j RPM Repository
    baseurl=https://yum.neo4j.com/stable/5
    enabled=1
    gpgcheck=1
    gpgkey=https://debian.neo4j.com/neotechnology.gpg.key

    # OR
    rpm --import https://debian.neo4j.com/neotechnology.gpg.key
    cat << EOF >  /etc/yum.repos.d/neo4j.repo
    [neo4j]
    name=Neo4j RPM Repository
    baseurl=https://yum.neo4j.com/stable/5
    enabled=1
    gpgcheck=1
    EOF

    # 2. install
    sudo yum install -y neo4j-5.16.0
    # (skip. enterprise) yum install neo4j-enterprise-5.16.0
    # (skip. enterprise) NEO4J_ACCEPT_LICENSE_AGREEMENT=yes yum install neo4j-enterprise-5.16.0

    # 3. set default password
    # The default minimum password length is 8 characters. Use the `dbms.security.auth_minimum_password_length` configuration to change it.
    # !!! Trick
    # 3.1 username is `neo4j`
    #     neo4j-admin dbms set-initial-password test@1234
    # 3.2 this must be done prior to first neo4j start up.
    # 3.3 if this is NOT done prior to first neo4j start up, the default password will be `neo4j`.
    # 3.4 if this is NOT done prior to first neo4j start up, for first logon, user will be required to change password
    cypher-shell -u neo4j -p neo4j
    > Password change required
    > new password: test@1234
    > confirm password: test@1234
    #
    # neo4j-admin dbms set-initial-password <password> [--require-password-change]
    neo4j-admin dbms set-initial-password test@1234

    # 4. enable service
    sudo systemctl status neo4j
    sudo systemctl enable neo4j
    sudo systemctl start neo4j
    sudo systemctl status neo4j
    sudo systemctl restart neo4j

    # 5. CQL - Cypher Query Language
    cypher-shell -u neo4j -p test@1234
    > Connected to Neo4j using Bolt protocol version 5.4 at **<neo4j://localhost:7687>** as user `neo4j`.
    > Type `:help` for a list of available commands or `:exit` to exit the shell.
    > Note that Cypher queries must end with a **semicolon**.

    :help
    :exit

    # 9. remove
    # sudo yum remove purge neo4j

### `Neoj4` username and password

The default minimum password length is 8 characters. Use the `dbms.security.auth_minimum_password_length` configuration to change it.

1. default username is `neo4j`
2. default password is `neo4j` --- if and only if (iff) you forgot to set default password prior to first start of `neo4j`.
3. cmd to set default password: `neo4j-admin dbms set-initial-password test@1234 [--require-password-change]` or `neo4j-admin dbms set-initial-password test@1234`.
4. if this is **NOT** done prior to first neo4j start up, for first logon, user will be required to change password.

### 1. cmd to set default password

    neo4j-admin dbms set-initial-password test@1234
    # OR
    neo4j-admin dbms set-initial-password test@1234 [--require-password-change]

### 2. Change password first time user logon

    cypher-shell -u neo4j -p neo4j
    > Password change required
    > new password: test@1234
    > confirm password: test@1234

### 3. Trick

    # !!! Trick
    # 3.1 username is `neo4j`
    #     neo4j-admin dbms set-initial-password test@1234
    # 3.2 this must be done prior to first neo4j start up.
    # 3.3 if this is NOT done prior to first neo4j start up, the default password will be `neo4j`.
    # 3.4 if this is NOT done prior to first neo4j start up, for first logon, user will be required to change password
    cypher-shell -u neo4j -p neo4j
    > Password change required
    > new password: test@1234
    > confirm password: test@1234
