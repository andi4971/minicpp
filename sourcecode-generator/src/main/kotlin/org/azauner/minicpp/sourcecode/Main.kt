package org.azauner.minicpp.sourcecode

import java.nio.file.Files
import kotlin.io.path.Path

fun main() {
    val fileName = "Sieve.mcpp"
    val inputStream = ClassLoader.getSystemResourceAsStream(fileName)
    val className = fileName.substringBefore(".mcpp")


    //walker.walk(MiniCppListener(parser), parser.miniCpp())
    val result = org.azauner.minicpp.ast.generator.generateASTForFile(inputStream, className)
    //println(result.prettyPrint())
    Files.writeString(Path("examples/output.mcpp"), result.generateSourceCode())

}
