# Spring Boot Tools (Extension) Warn

## Warn

Failed to fetch Generation from Spring IO: I/O error on GET request for <https://api.spring.io/projects>:
PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target

Source: Spring Boot Tools (Extension)

## Solution

    # settings.json:
    "java.jdt.ls.java.home": "C:/D/Tools/jdk-21.0.3",       <=== for `jdk home`
    "spring-boot.ls.java.home": "C:/D/Tools/jdk-21.0.3",    <=== for `Spring Boot Tools (Extension)`

## This does not work

    -Djava.net.useSystemProxies=true
