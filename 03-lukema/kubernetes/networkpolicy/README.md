# Network Policy

[Network Policy]<https://kubernetes.io/docs/concepts/services-networking/network-policies/>

## Default policies

By default, if no policies exist in a namespace, then all ingress and egress traffic is allowed to and from pods in that namespace.

    kubectl get all --namespace=kube-system
    kubectl get pods --namespace=kube-system
    kubectl get NetworkPolicy --namespace=kube-system
    kubectl get networkpolicy --namespace=kube-system
    kubectl describe networkpolicy <networkpolicy-name>

## Change Default Network Policy

    #######################################
    # By default, if no policies exist in a namespace, then all ingress and egress traffic is allowed to and from pods in that namespace.
    #######################################
    # deny all ingress traffic
    ---
    apiVersion: networking.k8s.io/v1
    kind: NetworkPolicy
    metadata:
      name: default-deny-ingress
    spec:
      podSelector: {}
      policyTypes:
      - Ingress
  
    # allow all ingress traffic
    ---
    apiVersion: networking.k8s.io/v1
    kind: NetworkPolicy
    metadata:
      name: allow-all-ingress
    spec:
      podSelector: {}
      ingress:
      - {}
      policyTypes:
      - Ingress
    
    # deny all egress traffic
    ---
    apiVersion: networking.k8s.io/v1
    kind: NetworkPolicy
    metadata:
      name: default-deny-egress
    spec:
      podSelector: {}
      policyTypes:
      - Egress
    
    # allow all egress traffic
    ---
    apiVersion: networking.k8s.io/v1
    kind: NetworkPolicy
    metadata:
      name: allow-all-egress
    spec:
      podSelector: {}
      egress:
      - {}
      policyTypes:
      - Egress
    
    # deny all ingress and all egress traffic
    ---
    apiVersion: networking.k8s.io/v1
    kind: NetworkPolicy
    metadata:
      name: default-deny-all
    spec:
      podSelector: {}
      policyTypes:
      - Ingress
      - Egress
