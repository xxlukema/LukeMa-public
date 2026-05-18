# All Aoubt Template

## Example 1

    apiVersion: apps/v1
    kind: Deployment
    metadata:
      name: nginx-deployment
      labels:
        app: web
    spec:
      selector:
        matchLabels:
          app: web
      replicas: 5
      strategy:
        type: RollingUpdate
      template:
        metadata:
          labels:
            app: web
        spec:
          containers:
           —name: nginx
              image: nginx
              ports:
               —containerPort: 80

### Explain 1

- `.spec.replicas` - how many pods to run
- `.strategy.type` - deployment strategy. examples: `RollingUpdate`
- `.spec.template.spec.containers` - container image to run in each of the pods and ports to expose.
- `.spec.selector.matchLabels` and `.spec.template.metadata.labels` - both of these must match and are referenced by the headless Service to route requests.
- `.spec.serviceName` - Mandatory for `StatefulSet` **without** Service. This headless service must exist before the `StatefulSet`.
- `matchLabels` is NOT supported yet by the service
- **service** binds with **pods** using `selectors`

### `StatefulSet`

- `StatefulSet` requires a **Kubernetes Headless Service** instead of a standard Kubernetes service. (aka. `StatefulSet` without explicit **Headless** Service)
- Setting `clusterIP` to `None` is what makes the **service** **headless**.
- pod name of `StatefulSet` will be `StatefulSet.name + - + "ordinal index"`.
- The "ordinal index" is a number starting from 0 for the first pod created by the StatefulSet and is incremented by one for each additional replica pod.
- Sample pod name: `quarkus-statefulset-0`, `quarkus-statefulset-1`, `quarkus-statefulset-3`

    kubectl get statefulsets
    kubectl describe endpoints quarkus-statefulset
    kubectl describe pod quarkus-statefulset-2
    IP=$(minikube ip -p devnation)
    PORT=$(kubectl get service/quarkus-statefulset-2 -o jsonpath="{.spec.ports[*].nodePort}")
    curl $IP:$PORT
