plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
//    id("systems.kuu.license-aggregator")
//    id("systems.kuu.license-aggregator") version "1.0.0"
}

android {
    namespace = "system.kuu.mylibrary"
    compileSdk = 33

    defaultConfig {
        minSdk = 27

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

//buildscript {
//    repositories {
//        google()
//        mavenCentral()
//        mavenLocal()
//    }
//    dependencies {
//        classpath("systems.kuu:license-aggregator:1.0.0")
//    }
//}

apply(plugin = "systems.kuu.license-aggregator.analyzer")
apply(plugin = "systems.kuu.license-aggregator.generator")

dependencies {
//    implementation("systems.kuu.license-aggregator:1.0.0")
//    ksp(project(":license-aggregator"))

    implementation(project(":license-aggregator-compose"))

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

