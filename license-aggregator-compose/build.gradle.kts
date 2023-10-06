plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
}

android {
    namespace = "systems.kuu.licenseaggregator.compose"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.5"
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2023.09.02"))

    implementation("androidx.compose.foundation:foundation:1.5.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.2")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.2")

    implementation("androidx.compose.material3:material3:1.1.2")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "systems.kuu.license-aggregator"
            artifactId = "compose"
            version = "0.0.1"
//            repositories {
//                maven {
//                    val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
//                    val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
//                    url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
//                    credentials {
//                        username =
//                            System.getenv("sonatype_username") ?: project.findProperty("sonatype_username") as? String
//                                    ?: ""
//                        password =
//                            System.getenv("sonatype_password") ?: project.findProperty("sonatype_password") as? String
//                                    ?: ""
//                    }
//                }
//            }
//            from(components["java"])
//            versionMapping {
//                usage("java-api") {
//                    fromResolutionOf("runtimeClasspath")
//                }
//                usage("java-runtime") {
//                    fromResolutionResult()
//                }
//            }
//            pom {
//                name.set("JpNHolidayK")
//                description.set("A concise description of my library")
//                packaging = "jar"
//                url.set(ProjectProperties.Url.site)
//                version = ProjectProperties.versionName
//                licenses {
//                    license {
//                        name.set("The Apache License, Version 2.0")
//                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
//                    }
//                }
//                developers {
//                    developer {
//                        id.set("kuu")
//                        name.set("Kuu")
//                        email.set("fumiya.kume@hotmail.com")
//                    }
//                }
//                scm {
//                    connection.set("scm:git:git@github.com:fumiya-kume/JpNHolidayK.git")
//                    developerConnection.set("scm:git:ssh://github.com:fumiya-kume/JpNHolidayK.git")
//                    url.set(ProjectProperties.Url.site)
//                }
//            }
        }
    }
}
