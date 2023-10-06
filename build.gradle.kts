plugins {
    id("com.android.application") version "8.2.0-beta06" apply false
    id("com.android.library") version "8.2.0-beta06" apply false
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