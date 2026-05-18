# Gradle

[Gradle Scripts](https://docs.gradle.org/current/userguide/writing_build_scripts.html)
[Gradle Build Language Reference](https://docs.gradle.org/current/dsl/index.html)

    gradle -q check

## 'gradle build' --- for war 'with main manifest attribute'
  
    gradle build
    cd build/libs
    java -jar hello-boot-gradle-1.0.1.war

## `gradle war` --- do not use

    # Do not use 'gradle war'. 'gradle war' will generate 'plain' war, that cannot be run using 'java -jar warname.war'
    gradle war
    cd build/libs
    java -jar hello-boot-gradle-1.0.1-plain.war
    no main manifest attribute, in hello-boot-gradle-1.0.1-plain.war

## swagger

<https://localhost:8443/swagger-ui/>
