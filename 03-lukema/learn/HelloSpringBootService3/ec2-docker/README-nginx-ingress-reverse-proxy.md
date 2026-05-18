# `nginx` ingress reverse proxy tricks

!!! Tricks

1. proxy listens to port 443
2. for all web path, forward traffic to port 9443
3. for all `/sping/**` path, forward traffic to port 8443
4. security group `inbound rules` must make ports 443, 8443, 9443 accessible to all-ipv4 traffic from the world (0.0.0.0/0)
5. Inside `default-nginx-ingress.conf`, `server_name  52.3.85.231;`   # !!! Trick: It is fullname of host, or ip address of host, but NOT localhost.
