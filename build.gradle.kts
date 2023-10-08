plugins {
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.20" apply false
}

buildscript {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }

    dependencies{
//        classpath("systems.kuu:license-aggregator:1.0.0")
    }
}

subprojects{
    group = "systems.kuu.license-aggregator"
    version = "0.0.6"
}