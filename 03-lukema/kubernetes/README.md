# Kubernetes

[Marcel Dempers' GitHub Source Code]<https://github.com/marcel-dempers/docker-development-youtube-series.git>

## CPU Limit

`milliCPU`: 0.1 === 100m (minimum 1m) (10% CPU). 1 === 1000m (1 CPU). m = millicpu = millicores

1 CPU unit is equivalent to 1 **physical CPU core**, or 1 virtual core, depending on whether the node is a physical host or a virtual
machine running inside a physical machine.

The expression 0.1 is equivalent to the expression 100m, which can be read as "one hundred millicpu" (some may say "one hundred millicores",
and this is understood to mean the same thing when talking about Kubernetes). A request with a decimal point, like 0.1 is converted to 100m by the API,
and precision finer than 1m is not allowed.

## Memory Limit

These are the same: `128974848, 129e6, 129M,  128974848000m, 123Mi`

G = Gigabyte. 1G = 1,000,000,000 bytes
M = Megabyte. 1M = 1,000,000 bytes
K = Kilobyte. 1K = 1,000 bytes

Gi = GiB = Gibibyte. 1Gi = 2³⁰ = 1,073,741,824 bytes
Mi = MiB = Mebibyte. 1Mi = 2²⁰ = 1,048,576 bytes
Ki = KiB = Kibibyte. 1Ki = 2¹⁰ = 1,024 bytes

## Five types of Services

1. **ClusterIP (default. to be used only on control-plane test)**: Internal clients send requests to a stable internal IP address.
2. **NodePort (for test)**: Clients send requests to the IP address of a node on one or more nodePort values that are specified by the Service.
3. **LoadBalancer (for production)**: Clients send requests to the IP address of a network load balancer.
4. **ExternalName**: Internal clients use the DNS name of a Service as an alias for an external DNS name.
5. **Headless**: You can use a headless service when you want a Pod grouping, but don't need a stable IP address.

- `externalIPs`: without `externalIPs`, service will be only accessible by "http://localhost:8080" only
- `externalIPs`: with `externalIPs`, service will be accessible from outside by "http://192.168.1.201:8080" or "http://ubuntu-k8s-master:8080"

## Cheatsheet

[Cheatsheet]<https://kubernetes.io/docs/reference/kubectl/cheatsheet/>

## Ingress

### Why Ingress use endpoints and not services?

The NGINX ingress controller does not use Services to route traffic to the pods. Instead it uses the Endpoints API in order to bypass kube-proxy
to allow NGINX features like session affinity and custom load balancing algorithms. It also removes some overhead, such as conntrack entries for
iptables DNAT.

## `PersistentVolume`

    # storageClassName: local
    storageClassName: hostpath
    capacity:
      storage: 1Gi
    accessModes:
      - ReadWriteOnce
    hostPath:
      path: "mnt/postgres/data"

    #
    k get storageClass
    k -n myns get pv

### `PersistentVolumeClaim`

    apiVersion: v1
    kind: PersistentVolumeClaim
    metadata:
      name: example-claim
    specs:
      # storageClassName: local 
      storageClassName: hostpath
      accessModes:
        - ReadWriteOnce
      hostPath:
        path: "mnt/postgres/data"
      resources:
        requests:
          storage: 50Mi

    #
    k -n myns get pvc
