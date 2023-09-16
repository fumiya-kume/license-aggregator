plugins {
    kotlin("jvm")
}

buildscript {

    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }

    dependencies{
        classpath("systems.kuu:license-aggregator:1.0.0")
    }
}