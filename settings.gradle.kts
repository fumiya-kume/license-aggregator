pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
//    plugins{
//        kotlin("jvm") version "1.9.0"
//        id("com.google.devtools.ksp") version "1.9.0-1.0.11"
//        id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
//        id("org.jetbrains.kotlin.android") version "1.8.10" apply false
////        id("com.android.library")
////        id("systems.kuu.license-aggregator") version "1.0.0"
//    }
}



dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}


//rootProject.name = "license-aggregator"
include("workload")
include("license-aggregator")
include(":test")
