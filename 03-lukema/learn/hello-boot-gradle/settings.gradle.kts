
/**
 * When central plugin repos were blocked in Freddie Mac, use this block to specify artifactory repo
 *
 */
/*
pluginManagement {
    repositories {
        maven {
            url "./maven-repo"
        }
        gradlePluginPortal()
        ivy {
            url "./ivy-repo"
        }
    }
}
*/

/**
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
*/

rootProject.name = "hello-boot-gradle"
// include("lib")
include("app")

plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}
