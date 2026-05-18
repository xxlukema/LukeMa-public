# Spring Boot Tools (Extension) Warn

## Warn

Failed to fetch Generation from Spring IO: I/O error on GET request for <https://api.spring.io/projects>:
PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target

Source: Spring Boot Tools (Extension)

## Solution

[Solution]<>

Use `settings.xml` to set proxy in `.m2` dir.

## This does not work

    -Djava.net.useSystemProxies=true
