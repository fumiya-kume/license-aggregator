plugins {
    kotlin("jvm")
    `maven-publish`
    `kotlin-dsl`
}

group = "systems.kuu"
version = "0.0.4"

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("com.android.tools.build:gradle:7.4.2")
//    implementation("com.android.tools.build:gradle-api:8.1.2")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(11)

}

//gradlePlugin {
//    plugins {
//        create("licenseAggregator") {
//            id = "systems.kuu.license-aggregator"
//            implementationClass = "systems.kuu.LicenseAggregatorPlugin"
//        }
//    }
//}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "systems.kuu.license-aggregator"
            artifactId = "plugin"
            version = "0.0.4"
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