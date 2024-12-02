plugins {
    kotlin("jvm")
}

group = "org.azauner"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":ast-generator"))
    implementation("org.ow2.asm:asm:9.7")
    implementation("org.ow2.asm:asm-util:9.7")
    testImplementation(kotlin("test"))
    implementation("org.junit.jupiter:junit-jupiter-params:5.0.0")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
