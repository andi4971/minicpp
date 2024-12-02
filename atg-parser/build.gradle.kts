plugins {
    kotlin("jvm")
}

group = "org.azauner"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api("org.antlr:antlr4:4.13.1")
    implementation(project(":ast"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
