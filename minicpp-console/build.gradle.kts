plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "8.1.1"

}

group = "org.azauner"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":ast-generator"))
    implementation(project(":bytecode-generator"))
    implementation(project(":sourcecode-generator"))
    testImplementation(kotlin("test"))
}

tasks {
    shadowJar {
        archiveClassifier.set("all")
        manifest {
            attributes(mapOf("Main-Class" to "org.azauner.minicpp.console.MiniCppConsoleKt"))
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
