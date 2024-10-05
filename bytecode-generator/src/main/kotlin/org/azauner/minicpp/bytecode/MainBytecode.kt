package org.azauner.minicpp.bytecode

import kotlin.io.path.Path
import kotlin.io.path.inputStream

fun main() {
    //val fileName = "Sieve.mcpp"
    val fileName = "GoLife.mcpp"
    val inputStream = Path("examples/$fileName").inputStream()
    val className = fileName.substringBefore(".mcpp")


    //walker.walk(MiniCppListener(parser), parser.miniCpp())
    val result = org.azauner.minicpp.ast.generator.generateASTForFile(inputStream, className)
    val bytes = MiniCppGenerator(result).generateByteCode()
    Path("examples/${result.className}.class").toFile().writeBytes(bytes)
}
