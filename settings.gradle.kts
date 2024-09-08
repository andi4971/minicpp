plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "minicpp"
include("parser")
include("ast-generator")
include("bytecode-generator")
include("sourcecode-generator")
include("bytecode-generator")
