plugins {
  application

  // This is equivalent to specify spring-boot-starter-parent version
  id("org.springframework.boot") version "3.5.6"
  // spring boot dependency version manager
  // id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

apply(plugin = "application")
// apply plugin: "io.spring.dependency-management"
// apply plugin: "war"

apply(plugin = "io.spring.dependency-management")

group "com.freddiemac.gradle"
version "1.0.1"

application {
    // Define the main class for the application.
    mainClass = "com.freddiemac.gradle.main.HelloBootGradleMainApplication"
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter("5.10.0")
        }
    }
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}


repositories {
    // The RepositoryHandler.jcenter() method has been deprecated. This is scheduled to be removed in Gradle 8.0.
    // JFrog announced JCenter"s shutdown in February 2021. Use mavenCentral() instead.
    // jcenter()
    mavenCentral()
}

configuration.all {
    // exclude group: "org.springframework.boot", module: "spring-boot-starter-tomcat"
    
    resolveStrategy.eachDependency {
        // if (requested.group == "org.springframework.security" && requested.name.startsWith("spring-security-core")) {
        //     useVersion("6.5.5")
        // }
        if (requested.group == "org.springframework.security" && requested.name == "spring-security-core") {
            useVersion("6.5.5")
        }
    }
}

dependencies {
    // Use JUnit test framework.
    // testImplementation "junit:junit:4.13"
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    // api "org.apache.commons:commons-math3:3.6.1"

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-aop")

    // open api
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    // swagger
    implementation("org.thingsboard:springfox-boot-starter:3.0.4")
    implementation("org.apache.commons:commons-lang3")

    // devtools
    compileOnly("org.springframework.boot:spring-boot-devtools")

    implementation("org.springframework.data:spring-data-commons")
    implementation("org.ehcache:ehcache:3.10.8")
    implementation("org.apache.tika:tika-core:2.9.1")

    implementation("javax:javaee-web-api:8.0.1")
    implementation("org.postgresql:postgresql")

    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
    implementation("org.glassfish:javax.json:1.1.4")
    implementation("org.eclipse:yasson")

    implementation("com.github.spotbugs:spotbugs-annotations:4.8.3")

    compileOnly("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")


    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    // mockito java agent to mock final classes and methods
    mockitoAgent("org.mockito:mockito-core") { isTransitive = false }
}

// Source - https://stackoverflow.com/a/79668033
// Posted by davidlj95
// Retrieved 2025-12-01, License - CC BY-SA 4.0

val mockitoAgent = configurations.create("mockitoAgent")

tasks.withType<Test> {
     // ...
     jvmArgs("-javaagent:${mockitoAgent.asPath}")
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

