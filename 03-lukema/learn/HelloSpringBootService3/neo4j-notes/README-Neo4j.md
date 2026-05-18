# Neo4j Notes

## Best Practices

- Security implications
- Do not create or retain more objects than you strictly need to. Large caches in particular tend to promote more objects to the old generation.
- Do not use internal Neo4j APIs. They are internal to Neo4j and subject to change without notice, which may break or change the behavior of your code.
- If possible, avoid using Java object serialization or reflection in your code or in any runtime dependency that you use.
- If you cannot avoid using Java object serialization and reflection, ensure that the `-XX:+TrustFinalNonStaticFields` JVM flag is disabled in neo4j.conf.

## Notes

a node can have many labels

label: Class
properties: key in key:value pairs

relationship has names
relationship has properties

## Machine learning

### Patterns

### Trends

### Anomalies
