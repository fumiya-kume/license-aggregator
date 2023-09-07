plugins {
    kotlin("jvm")
}

//group = "systems.kuu"
//version = "1.0-SNAPSHOT"

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.0-1.0.11")

    testImplementation(kotlin("test"))
}
