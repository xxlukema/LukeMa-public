# ConfigMap

[tutorial]<https://earthly.dev/blog/kubernetes-config-maps/>

ConfigMap can be applied to pods as:

1. environment variables
2. files in a mounted volume
3. ConfigMaps can also be immutable, to (1) forbids dynamic updates, and (2) enhance safety and performance.
4. ConfigMaps can contain `binaryData`, with the condition that the `binaryData` must be converted to base64 string
5. created from files, database, or literal

## Examples

    ---
    
    # `binaryData` as base64-encoded string
    apiVersion: v1
    kind: ConfigMap
    metadata:
      name: app-config
    data:
      db_host: https://database.example.com
      default_user_status: suspended user
      max_invoice_date: "2022-12-31"
      default_command: date
    binaryData:
      demo: ZXhhbXBsZQo=
    
    ---
    
    # access key-values from pod
    apiVersion: v1
    kind: Pod
    metadata:
      name: app-pod
    spec:
      containers:
        - name: app-container
          command: ["/bin/sh", "-c", "echo key:db_host, value:$db_host"]
          image: busybox:latest
          resources:
            limits:
              memory: 512Mi
              cpu: "1"
            requests:
              memory: 256Mi
              cpu: "0.2"
          envFrom:
            - configMapRef:
                name: app-config
        
    ---
    # rename key
    apiVersion: v1
    kind: Pod
    metadata:
      name: app-pod
    spec:
      containers:
        - name: app-container
          image: busybox:latest
          env:
            - name: ACCOUNTS_INVOICING_MAX_DATE
            - valueFrom:
                configMapKeyRef:
                   name: app-config
                   key: max_invoice_date
    
    ---
    # Mounted Volume Files
    apiVersion: v1
    kind: Pod
    metadata:
      name: app-pod
    spec:
      containers:
        - name: app-container
          image: busybox:latest
          volumeMounts:
             - name: config
               mountPath: "/etc/demo-app"
               readOnly: true
          volumes:
             - name: config
               configMap:
                 name: app-config
    
    ---
    # Command Line Arguments
    apiVersion: v1
    kind: Pod
    metadata:
      name: app-pod
    spec:
      containers:
        - name: app-container
          image: busybox:latest
          command: ["/bin/sh", "-c", "$(STARTUP_COMMAND)"]
          env:
             - name: STARTUP_COMMAND
             - valueFrom:
                 configMapKeyRef:
                   name: app-config
                   key: default_command
    
    ---
    # Immutable ConfigMaps
    apiVersion: v1
    kind: ConfigMap
    metadata:
      name: app-config
    data:
      db_host: "https://database.example.com"
    immutable: true
    
    ---
    # Viewing ConfigMap Data
    kubectl describe configmap app-config
    
    ---
    # Creating ConfigMaps from the Command Line
    kubectl create configmap app-config --from-literal=db_host=https://database.example.com
    kubectl create configmap app-config --from-file=./app.conf
    kubectl create configmap app-config --from-env-file=application.properties
    
    # ./app.conf:
    db_host=https://database.example.com
    default_user_status=suspended
    kubectl create configmap app-config --from-file=./conf/

    k get configmap
    k get configmap -n efk-logging
