package org.azauner.minicpp.bytecode

import kotlin.io.path.Path
import kotlin.io.path.inputStream

fun main() {
    val fileName = "Sieve.mcpp"
    //val fileName = "GoLife.mcpp"
    //val fileName = "BubbleSort.mcpp"
    var inputStream = Path("examples/$fileName").inputStream()
    val className = fileName.substringBefore(".mcpp")


    //walker.walk(MiniCppListener(parser), parser.miniCpp())
    val result2 = org.azauner.minicpp.ast.generator.generateAstForFileListener(inputStream, className)
    inputStream = Path("examples/$fileName").inputStream()
    val result = org.azauner.minicpp.ast.generator.generateASTForFileVisitor(inputStream, className)
    val bytes = MiniCppGenerator(result).generateByteCode()
    val bytes2 = MiniCppGenerator(result2).generateByteCode()
    Path("examples/${result.className}_list.class").toFile().writeBytes(bytes2)
    Path("examples/${result.className}.class").toFile().writeBytes(bytes)
}
