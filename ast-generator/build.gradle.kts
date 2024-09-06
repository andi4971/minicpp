plugins {
    kotlin("jvm")
    id("io.freefair.aspectj.post-compile-weaving") version "8.10"
}

group = "org.azauner"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    api(project(":parser"))
    implementation("org.ow2.asm:asm:9.7")
    implementation("org.ow2.asm:asm-util:9.7")
    implementation("org.aspectj:aspectjrt:1.9.21")
    implementation("org.aspectj:aspectjweaver:1.9.21")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
