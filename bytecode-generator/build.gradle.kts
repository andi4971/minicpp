plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.azauner"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks {
    shadowJar {
        archiveClassifier.set("all")
        manifest {
            attributes(mapOf("Main-Class" to "org.azauner.minicpp.bytecode.MainBytecodeKt"))
        }
    }
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
