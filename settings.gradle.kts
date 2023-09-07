pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    plugins{
        kotlin("jvm") version "1.9.0"
        id("com.google.devtools.ksp") version "1.9.0-1.0.11"
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}


plugins {
//    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

//rootProject.name = "license-aggregator"
include("workload")
include("license-aggregator")
